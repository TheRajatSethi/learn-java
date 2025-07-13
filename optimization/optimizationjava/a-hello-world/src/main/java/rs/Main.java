package rs;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.SolverConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SolverFactory<TeacherRoster> solverFactory = SolverFactory.create(new SolverConfig()
                .withSolutionClass(TeacherRoster.class)
                .withEntityClasses(School.class)
                .withConstraintProviderClass(CP.class)
                .withTerminationSpentLimit(Duration.ofSeconds(2)));

        List<Teacher> inputTeachers = List.of(
                new Teacher("Sam", 200_000),
                new Teacher("Pam", 150_000),
                new Teacher("Tam", 60_000),
                new Teacher("Lam", 50_000),
                new Teacher("Nam", 60_000),
                new Teacher("Ram", 90_000),
                new Teacher("Zam", 20_000),
                new Teacher("Cam", 10_000)
        );

        List<School> schools = new ArrayList<>();
        for (int i=1;i<=2;i++){
            var s = new School();
            s.id = (long) i;
            schools.add(s);
        }

        TeacherRoster s = new TeacherRoster();
        s.setInputTeachers(inputTeachers);
        s.schools = schools;

        Solver<TeacherRoster> solver = solverFactory.buildSolver();
        TeacherRoster teacherRoster = solver.solve(s);

        System.out.println("-------------------");
        System.out.println(teacherRoster.schools);
        System.out.println(teacherRoster.hardSoftScore);
        System.out.println(teacherRoster.inputTeachers);
    }
}