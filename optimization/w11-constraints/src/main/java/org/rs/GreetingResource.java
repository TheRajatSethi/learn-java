package org.rs;

import ai.timefold.solver.core.api.solver.SolverJob;
import ai.timefold.solver.core.api.solver.SolverManager;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Time;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Path("/timeTable")
public class GreetingResource {

    @Inject
    SolverManager<TimeTable, UUID> solverManager;

    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
    public TimeTable hello(TimeTable problem) {


        System.out.println("Invoked");
        UUID problemId = UUID.randomUUID();
        // Submit the problem to start solving
        SolverJob<TimeTable, UUID> solverJob = solverManager.solve(problemId, problem);
        TimeTable solution;
        try {
            // Wait until the solving ends
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException("Solving failed.", e);
        }
        return solution;
//        return "ok " + data;
    }
}
