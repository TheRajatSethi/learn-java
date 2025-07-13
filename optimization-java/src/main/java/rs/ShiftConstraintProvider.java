package rs;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;


import java.util.*;
import java.util.function.Function;


enum Constraints{
    UniqueEmployeePerShift,
    AssignWithManySkills,
    DoNotAssignManager
}

class ConstraintsMapping{
    static EnumMap<Constraints, Function<ConstraintFactory, Constraint>> mapping = new EnumMap<>(Constraints.class);

    static EnumMap<Constraints, Function<ConstraintFactory, Constraint>> getMapping(){
        mapping.put(Constraints.AssignWithManySkills, ShiftConstraintFactory.some);
        return mapping;
    };
}

@NoArgsConstructor
public class ShiftConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint @NonNull [] defineConstraints(@NonNull ConstraintFactory constraintFactory) {
        List<Constraint> constraints = new ArrayList<>();
        if (true){ // Based on company rules
            constraints.add(ShiftConstraintFactory.uniqueEmployeePerShift(constraintFactory));
            constraints.add(ShiftConstraintFactory.assignWithManySkills(constraintFactory));
            constraints.add(ShiftConstraintFactory.doNotAssignManager(constraintFactory));
        }
        return constraints.toArray(Constraint[]::new);
    }
}


class ShiftConstraintFactory {

    ///  HARD constraint that 1 shift position can be done by only 1 employee
    static Constraint uniqueEmployeePerShift(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(
                        Shift.class,
                        Joiners.equal(Shift::getGroup),
                        Joiners.equal(Shift::getEmployee))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("uniqueEmployeePerShift");
    }

    /// Prefer employee who has many skills
    static Constraint assignWithManySkills(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
                .map(Shift::getEmployee)
                .filter(e -> e.getSkills().length > 3)
                .reward(HardSoftScore.ONE_SOFT)
                .asConstraint("assignWithManySkills");
    }

    /// Prefer not to assign manager - employee is expensive
    static Constraint doNotAssignManager(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
                .filter(s -> s.getEmployee().getLevel().equals(Level.MANAGER))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("doNotAssignManager");
    }

    static Function<ConstraintFactory, Constraint> some = new Function<ConstraintFactory, Constraint>() {
        @Override
        public Constraint apply(ConstraintFactory constraintFactory) {
            return constraintFactory.forEach(Shift.class)
                    .filter(s -> s.getEmployee().getLevel().equals(Level.MANAGER))
                    .penalize(HardSoftScore.ONE_SOFT)
                    .asConstraint("doNotAssignManager");
        }
    };

}