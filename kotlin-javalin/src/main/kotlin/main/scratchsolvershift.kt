/**
 * Employee
 * - name
 * - start
 * - end
 * - level
 *
 * Shift
 * - start
 * - end
 * - level
 */


package main

import ai.timefold.solver.core.api.domain.entity.PlanningEntity
import ai.timefold.solver.core.api.domain.lookup.PlanningId
import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty
import ai.timefold.solver.core.api.domain.solution.PlanningScore
import ai.timefold.solver.core.api.domain.solution.PlanningSolution
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider
import ai.timefold.solver.core.api.domain.variable.PlanningVariable
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore
import ai.timefold.solver.core.api.score.stream.Constraint
import ai.timefold.solver.core.api.score.stream.ConstraintFactory
import ai.timefold.solver.core.api.score.stream.ConstraintProvider
import com.charleskorn.kaml.Yaml
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

fun main(){

    @Serializable
    data class Employee(val name: String, val start: LocalTime, val end: LocalTime, val level: Char){}

    @Serializable
    data class ShiftPosition(val id: Int, val start: LocalTime, val end: LocalTime, val position: Char){}

    @PlanningEntity
    @Serializable
    class Shift{
        @PlanningId
        var id: Int? = null
        var start: LocalTime = LocalTime.fromSecondOfDay(0)
        var end: LocalTime = LocalTime.fromSecondOfDay(0)
        var position: String = ""

        @PlanningVariable
        var employee: Employee? = null

        constructor()

        constructor(id: Int, start: LocalTime, end: LocalTime, position: String){
            this.start = start
            this.end = end
            this.id = id
            this.position = position
        }
    }

    @PlanningSolution
    class ShiftTimeTable{
        @ValueRangeProvider
        lateinit var employees: List<Employee>

        @PlanningEntityCollectionProperty
        lateinit var shifts: List<Shift>

        @PlanningScore
        var score: HardSoftScore? = null

        constructor() {}

        constructor(employees: List<Employee>, shifts: List<Shift>) {
            this.employees = employees
            this.shifts = shifts
        }

        override fun toString(): String {
            return "$employees $shifts"
        }
    }

    class ConsProvider: ConstraintProvider{
        override fun defineConstraints(constraintFactory: ConstraintFactory): Array<out Constraint?> {
            return arrayOf(consA(constraintFactory))
        }

        fun consA(cf: ConstraintFactory): Constraint{
            return cf.forEach(Shift::class.java)
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Some constraint")
        }
    }

    val employeesData = """
    - name: Sam
      start: '09:00'
      end: '18:00'
      level: M
    - name: Pam
      start: '09:00'
      end: '18:00'
      level: E
    - name: Tam
      start: '09:00'
      end: '18:00'
      level: E
    - name: Lam
      start: '14:00'
      end: '23:00'
      level: M
    - name: Zam
      start: '14:00'
      end: '23:00'
      level: E
    - name: Kam
      start: '14:00'
      end: '23:00'
      level: E
    """.trimIndent()

    val shiftsData = """
        - id: 1
          start: '09:00'
          end: '16:00'
          position: E
        - id: 2
          start: '09:00'
          end: '16:00'
          position: M
        - id: 3
          start: '16:00'
          end: '23:00'
          position: E
        - id: 4
          start: '16:00'
          end: '23:00'
          position: M
    """.trimIndent()

    val employees = Yaml.default.decodeFromString(ListSerializer(Employee.serializer()),employeesData)
    val shifts = Yaml.default.decodeFromString(ListSerializer(Shift.serializer()),shiftsData)

    print(shifts)
}
