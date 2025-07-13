package rs

import ai.timefold.solver.core.api.domain.entity.PlanningEntity
import ai.timefold.solver.core.api.domain.lookup.PlanningId
import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty
import ai.timefold.solver.core.api.domain.solution.PlanningScore
import ai.timefold.solver.core.api.domain.solution.PlanningSolution
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider
import ai.timefold.solver.core.api.domain.variable.PlanningVariable
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore
import ai.timefold.solver.core.api.solver.SolverFactory
import ai.timefold.solver.core.config.solver.SolverConfig
import com.charleskorn.kaml.Yaml
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.argument.AbstractArgumentFactory
import org.jdbi.v3.core.argument.Argument
import org.jdbi.v3.core.config.ConfigRegistry
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.withExtensionUnchecked
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import java.io.File
import java.sql.DriverManager
import java.sql.Types
import java.time.Duration


enum class Language {
    ENGLISH,
    FRENCH,
    HINDI,
    PUNJABI
}

enum class Level {
    MANAGER,
    EMPLOYEE,
}


enum class Skill {
    A, B, C, D, E, F, G, H, I, J
}

@Serializable
data class Employee(
    val id: Int,
    val name: String,
    val level: Level,
    val startAvailability: LocalTime,
    val endAvailability: LocalTime,
    val preferredStartAvailability: LocalTime,
    val preferredEndAvailability: LocalTime,
    val age: Int,
    val languagesSpoken: List<Language>,
    val skills: List<Skill>,
    val latitude: Double,
    val longitude: Double,
) {}


@Serializable
@PlanningEntity
data class Shift(
    @PlanningId
    var id: Int,
    var group: Int,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var level: Level,
    var latitude: Double,
    var longitude: Double
) {
    @PlanningVariable
    var employee: Employee? = null
}


@PlanningSolution
data class TimeTable(
    @ValueRangeProvider
    @ProblemFactCollectionProperty
    var employees: List<Employee>,
    @PlanningEntityCollectionProperty
    var shifts: List<Shift>
) {

    @PlanningScore
    var score: HardSoftScore? = null
}

object App{
    val con = DriverManager.getConnection("jdbc:sqlite:db.db")!!
//    val jdbi = Jdbi.create(con).installPlugin(KotlinPlugin()).installPlugin(SqlObjectPlugin())!!
    val jdbi = Jdbi.create(con).installPlugins()!!
}





fun main() {

//    val shiftsData = File("src/main/resources/shifts.yaml").readText(Charsets.UTF_8)
//    val employeesData = File("src/main/resources/employees.yaml").readText(Charsets.UTF_8)
//
//
//    val shifts = Yaml.default.decodeFromString(ListSerializer(Shift.serializer()), shiftsData)
//    val employees = Yaml.default.decodeFromString(ListSerializer(Employee.serializer()), employeesData)
//
//    val tt = TimeTable(employees, shifts)
//
//    val solverFactor = SolverFactory.create<TimeTable?>(
//        SolverConfig().withSolutionClass(TimeTable::class.java)
//            .withEntityClasses(Shift::class.java)
//            .withConstraintProviderClass(CP::class.java)
//            .withTerminationUnimprovedSpentLimit(Duration.ofMillis(1000))
//    )
//
//    var solution = solverFactor.buildSolver().solve(tt)
//    solution.shifts.forEach { println(it.employee) }



    App.jdbi.withExtensionUnchecked(JDBISessionRepository::class.java) {it -> it.createIntoSomeTable(SomeTable(1,"Sam"))}



}