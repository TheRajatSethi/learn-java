package rs;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class CP implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
            highSalaryConstraint(constraintFactory),
            uniqueTeacherConstraint(constraintFactory)
        };
    }

    private Constraint highSalaryConstraint(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(School.class)
                .penalize(HardSoftScore.ONE_SOFT, (s -> s.teacher.salary()))
                .asConstraint("Some");
    }

    /**
     * One of the problems I am facing with this is that I am not 100% sure if the code below is
     * working that why its working.
     */
    private Constraint uniqueTeacherConstraint(ConstraintFactory constraintFactory){
        return constraintFactory.forEachUniquePair(School.class,
                Joiners.equal(School::getTeacher))
                .filter((t1, t2) -> t1.teacher.name().equals(t2.teacher.name()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Duplicate teacher");
    }
}
