package rs

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore
import ai.timefold.solver.core.api.score.stream.Constraint
import ai.timefold.solver.core.api.score.stream.ConstraintFactory
import ai.timefold.solver.core.api.score.stream.ConstraintProvider
import ai.timefold.solver.core.api.score.stream.Joiners


///  HARD constraint that 1 shift position can be done by only 1 employee
fun uniqueEmployeePerShift(constraintFactory: ConstraintFactory): Constraint {
    return constraintFactory.forEachUniquePair<Shift?>(
        Shift::class.java,
        Joiners.equal<Shift?, Int?>(Shift::group),
        Joiners.equal<Shift?, Employee?>(Shift::employee)
    )
        .penalize<HardSoftScore?>(HardSoftScore.ONE_HARD)
        .asConstraint("uniqueEmployeePerShift")
}

/// Prefer employee who has many skills
fun assignWithManySkills(constraintFactory: ConstraintFactory): Constraint {
    return constraintFactory.forEach<Shift?>(Shift::class.java)
        .map<Employee?>(Shift::employee)
        .filter { e: Employee? -> e!!.skills.size > 3 }
        .reward<HardSoftScore?>(HardSoftScore.ONE_SOFT)
        .asConstraint("assignWithManySkills")
}

// Prefer not to assign manager - employee is expensive
fun doNotAssignManager(constraintFactory: ConstraintFactory): Constraint {
    return constraintFactory.forEach<Shift?>(Shift::class.java)
        .filter { s: Shift? -> s!!.employee!!.level == Level.MANAGER }
        .penalize<HardSoftScore?>(HardSoftScore.ONE_SOFT)
        .asConstraint("doNotAssignManager")
}


/**
 * Constraint Provider
 * @see doNotAssignManager
 */
class CP : ConstraintProvider {
    override fun defineConstraints(cf: ConstraintFactory): Array<Constraint> {
        return arrayOf(
            uniqueEmployeePerShift(cf),
            assignWithManySkills(cf),
            doNotAssignManager(cf),
        )
    }
}