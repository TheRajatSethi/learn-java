package main

import java.time.DayOfWeek
import java.time.LocalTime
import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.lookup.PlanningId
import ai.timefold.solver.core.api.domain.solution.PlanningSolution
import ai.timefold.solver.core.api.domain.variable.PlanningVariable
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore
import ai.timefold.solver.core.api.score.stream.Constraint
import ai.timefold.solver.core.api.score.stream.ConstraintFactory
import ai.timefold.solver.core.api.score.stream.ConstraintProvider
import ai.timefold.solver.core.api.score.stream.Joiners
import ai.timefold.solver.core.api.domain.solution.*;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider
import ai.timefold.solver.core.api.solver.Solver
import ai.timefold.solver.core.api.solver.SolverFactory
import ai.timefold.solver.core.config.solver.SolverConfig
import java.time.Duration


fun main() {

    data class Room(var name: String) {}

    data class Timeslot(var dayOfWeek: DayOfWeek, var startTime: LocalTime, var endTime: LocalTime) {}


    @PlanningEntity
    class Lesson {
        @PlanningId
        var id: Long? = null
        lateinit var subject: String
        lateinit var teacher: String
        lateinit var studentGroup: String

        @PlanningVariable
        var timeslot: Timeslot? = null

        @PlanningVariable
        var room: Room? = null

        constructor()

        constructor(id: Long, subject: String, teacher: String, studentGroup: String) {
            this.id = id
            this.subject = subject
            this.teacher = teacher
            this.studentGroup = studentGroup
        }
        override fun toString(): String = subject
    }

    class TTCP : ConstraintProvider {
        override fun defineConstraints(constraintFactory: ConstraintFactory): Array<out Constraint?> {
            return arrayOf(roomConflict(constraintFactory),
                teacherConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                teacherRoomStability(constraintFactory),
                teacherTimeEfficiency(constraintFactory),
                studentGroupSubjectVariety(constraintFactory))
        }

        fun roomConflict(cf: ConstraintFactory): Constraint {
            return cf.forEachUniquePair(
                Lesson::class.java,
                Joiners.equal(Lesson::timeslot),
                Joiners.equal(Lesson::room)
            ).penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Room Conflict")
        }

        fun teacherConflict(constraintFactory: ConstraintFactory): Constraint {
            // A teacher can teach at most one lesson at the same time.
            return constraintFactory
                .forEachUniquePair(
                    Lesson::class.java,
                    Joiners.equal(Lesson::timeslot),
                    Joiners.equal(Lesson::teacher)
                )
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Teacher conflict");
        }
        fun studentGroupConflict(constraintFactory: ConstraintFactory): Constraint {
            // A student can attend at most one lesson at the same time.
            return constraintFactory
                .forEachUniquePair(
                    Lesson::class.java,
                    Joiners.equal(Lesson::timeslot),
                    Joiners.equal(Lesson::studentGroup)
                )
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Student group conflict");
        }

        fun teacherRoomStability(constraintFactory: ConstraintFactory): Constraint {
            // A teacher prefers to teach in a single room.
            return constraintFactory
                .forEachUniquePair(
                    Lesson::class.java,
                    Joiners.equal(Lesson::teacher)
                )
                .filter { lesson1: Lesson, lesson2: Lesson -> lesson1.room !== lesson2.room }
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Teacher room stability");
        }
        fun teacherTimeEfficiency(constraintFactory: ConstraintFactory): Constraint {
            // A teacher prefers to teach sequential lessons and dislikes gaps between lessons.
            return constraintFactory
                .forEach(Lesson::class.java)
                .join(Lesson::class.java,
                    Joiners.equal(Lesson::teacher),
                    Joiners.equal { lesson: Lesson -> lesson.timeslot?.dayOfWeek })
                .filter { lesson1: Lesson, lesson2: Lesson ->
                    val between = Duration.between(
                        lesson1.timeslot?.endTime,
                        lesson2.timeslot?.startTime
                    )
                    !between.isNegative && between.compareTo(Duration.ofMinutes(30)) <= 0
                }
                .reward(HardSoftScore.ONE_SOFT)
                .asConstraint("Teacher time efficiency");
        }

        fun studentGroupSubjectVariety(constraintFactory: ConstraintFactory): Constraint {
            // A student group dislikes sequential lessons on the same subject.
            return constraintFactory
                .forEach(Lesson::class.java)
                .join(Lesson::class.java,
                    Joiners.equal(Lesson::subject),
                    Joiners.equal(Lesson::studentGroup),
                    Joiners.equal { lesson: Lesson -> lesson.timeslot?.dayOfWeek })
                .filter { lesson1: Lesson, lesson2: Lesson ->
                    val between = Duration.between(
                        lesson1.timeslot?.endTime,
                        lesson2.timeslot?.startTime
                    )
                    !between.isNegative && between.compareTo(Duration.ofMinutes(30)) <= 0
                }
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Student group subject variety");
        }

    }

    @PlanningSolution
    class TT{
        @ProblemFactCollectionProperty // Even with this commented out it works fine.
        @ValueRangeProvider
        lateinit var timeslots: List<Timeslot>
        @ProblemFactCollectionProperty // Even with this commented out it works fine.
        @ValueRangeProvider
        lateinit var rooms: List<Room>
        @PlanningEntityCollectionProperty
        lateinit var lessons: List<Lesson>

        @PlanningScore
        var score: HardSoftScore? = null

        constructor() {}

        constructor(timeslots: List<Timeslot>, rooms: List<Room>, lessons: List<Lesson>) {
            this.timeslots = timeslots
            this.rooms = rooms
            this.lessons = lessons
        }

        override fun toString(): String {
            return "$timeslots $rooms $lessons"
        }

    }

    val timeslots: MutableList<Timeslot> = mutableListOf(
        Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)),

        Timeslot(DayOfWeek.TUESDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)),
        Timeslot(DayOfWeek.TUESDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)),
        Timeslot(DayOfWeek.TUESDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)),
        Timeslot(DayOfWeek.TUESDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)),
        Timeslot(DayOfWeek.TUESDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)))


    val rooms: MutableList<Room> = mutableListOf(
        Room("Room A"),
        Room("Room B"),
        Room("Room C"))

    var nextId: Long = 0
    val lessons: MutableList<Lesson> = mutableListOf(
        Lesson(nextId++, "Math", "A. Turing", "9th grade"),
        Lesson(nextId++, "Math", "A. Turing", "9th grade"),
        Lesson(nextId++, "Physics", "M. Curie", "9th grade"),
        Lesson(nextId++, "Chemistry", "M. Curie", "9th grade"),
        Lesson(nextId++, "Biology", "C. Darwin", "9th grade"),
        Lesson(nextId++, "History", "I. Jones", "9th grade"),
        Lesson(nextId++, "English", "I. Jones", "9th grade"),
        Lesson(nextId++, "English", "I. Jones", "9th grade"),
        Lesson(nextId++, "Spanish", "P. Cruz", "9th grade"),
        Lesson(nextId++, "Spanish", "P. Cruz", "9th grade"),
        Lesson(nextId++, "Math", "A. Turing", "10th grade"),
        Lesson(nextId++, "Math", "A. Turing", "10th grade"),
        Lesson(nextId++, "Math", "A. Turing", "10th grade"),
        Lesson(nextId++, "Physics", "M. Curie", "10th grade"),
        Lesson(nextId++, "Chemistry", "M. Curie", "10th grade"),
        Lesson(nextId++, "French", "M. Curie", "10th grade"),
        Lesson(nextId++, "Geography", "C. Darwin", "10th grade"),
        Lesson(nextId++, "History", "I. Jones", "10th grade"),
        Lesson(nextId++, "English", "P. Cruz", "10th grade"),
        Lesson(nextId++, "Spanish", "P. Cruz", "10th grade"))

    val problem = TT(timeslots, rooms, lessons)

    val solverFactory: SolverFactory<TT> = SolverFactory.create(SolverConfig()
        .withSolutionClass(TT::class.java)
        .withEntityClasses(Lesson::class.java)
        .withConstraintProviderClass(TTCP::class.java)
        .withTerminationUnimprovedSpentLimit(Duration.ofMillis(400)))

    val solver: Solver<TT> = solverFactory.buildSolver()

    val solution: TT = solver.solve(problem)

    println(solution)

}

