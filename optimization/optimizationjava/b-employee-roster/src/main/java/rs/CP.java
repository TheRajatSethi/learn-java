package rs;

import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

public class CP implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
        };
    }
//
//    private Constraint vacation(ConstraintFactory constraintFactory){
//        constraintFactory.forEach(ShiftAssignment.class)
//                .
//    }

    private Constraint singleEmployeeEachDay(ConstraintFactory constraintFactory){
        constraintFactory.forEach(ShiftAssignment.class)

    }
}
