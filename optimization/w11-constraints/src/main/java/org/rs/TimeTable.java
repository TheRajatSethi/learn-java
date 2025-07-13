package org.rs;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class TimeTable {

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<TimeSlot> timeSlots;

    @ValueRangeProvider
    @ProblemFactCollectionProperty
    private List<Room> rooms;

    @PlanningEntityCollectionProperty
    private List<Lesson> lessons;

    @PlanningScore
    private HardSoftScore score;

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public HardSoftScore getScore() {
        return score;
    }
}
