package org.rs;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;

public class TimeTableConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
            roomConflict(constraintFactory)
        };
    }

    private Constraint roomConflict(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Lesson.class)
                // ... and pair it with another lesson ...
                .join(Lesson.class,
                        Joiners.equal(Lesson::getTimeslot),
                        Joiners.equal(Lesson::getRoom),
                        Joiners.lessThan(Lesson::getId))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Room conflict");
    }
}
