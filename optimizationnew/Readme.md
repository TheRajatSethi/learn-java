# Project

## To be developed
- logging
- ngnix
- images
- blog

## Issues

### Issue 1 : Duplicate actions on DB : TODO

Steps
- In the sign up handler you check if the account exists by email
- Null you create.

Let's say 2 threads execute at the same time. Both will insert in the db. This is Ok if the email field is marked as unique but not every field is constrained by the db. This creates a problem with threaded (Java) or multiprocess (Python) application.




__Possible Fix__
- For sensitive data always db constraints
- For non sensitive data let's say how many times this page was visited if there is a little bit slippage then may be not the end of the world. No need to introduce integrity key :)

---

# Reference

## Database Options

- JDBI - WON `sql-object` setup
- https://sqldelight.github.io/sqldelight/2.0.2/native_sqlite/
- JOOQ
- ktorm
- https://github.com/andrewoma/kwery


---

# Optimization

## 1. Modelling the problem - Abstract

- __Unrelated DataPoint/Class__ : There is some data in your domain, if this does not factor into constraints etc.. then it's irrelevant for planning.

- __Problem Fact__ : Problem fact is something which does not change during planning (as long as problem remains the same). E.g. what time is an employee available, what are the skill sets of the employee/teacher etc...

- __Planning Entity__ : It is something `class` which holds a collection of problem facts. Some of them are set on the instance of the planning entity and cannot be changed, other problem facts can be changed during the planning process are marked as planning variables (which change during the planning process).

## 2. Modelling the problem - In Code

### Problem Fact

- Can be defined as a simple class
- Can and should have `val` variables.
- Does not need any fancy constructor etc...
- Does not need any timefold/optaplanner annotations

Simple data class can be fine
```kotlin
data class Timeslot(var dayOfWeek: DayOfWeek, var startTime: LocalTime, var endTime: LocalTime) {}
```

### Planning Variable
- Should be annotated with `@PlanningVariable`
- Which instance of the planning varialble will be assigned to the planning entity will be changed during planning at each step for optimization.
- Can be genuine or computed (shadows).

`valueRangeProviderRefs` property defines what are the possible planning values for this planning variable. It references one or more `@ValueRangeProvider`



### Planning Entity
- Needs `@PlanningId` to uniquely identify this entity, (used for comparable)
- Needs 1 or move than 1 `@PlanningVariable` which timefold will change. Other info you can add to the instance
- Planning entity can have many members not all of them need to be planning variables i.e. not all will change during the planning process

E.g.
Let's say the lesson needs 5 things to be complete
- subject
- teacher
- student group / grade etc...
- timeslot
- room

Out of these the following 3 we already know and are relationship is fixed. E.g. For Math Mr Jones teaches Grade 8. Thus no need to set them as planning variable. For the combination of 3 we only need to figure out the room and timeslot.
- subject
- teacher
- student group / grade etc...

All we really want to plan is when and in which room Thus the `@PlanningVariable` is only set on room and timeslot.

Thus when we create a list of `lessons` we already put in subject, teacher, group and let timefold add room and timeslot for us during planning process e.g.

```kotlin
Lesson(1, "Math", "Mr Jones", "Grade-10")
```

Full class
```kotlin
@PlanningEntity
class Lesson {
    @PlanningId
    var id: Long? = null
    lateinit var subject: String
    lateinit var teacher: String
    lateinit var studentGroup: String

    @PlanningVariable
    var timeslot: Timeslot? = null

    @PlanningVariable
    var room: Room? = null

    // Timefold needs this for some reason.
    constructor()

    // For the ones which are not PlanningVariable
    constructor(id: Long, subject: String, teacher: String, studentGroup: String) {
        this.id = id
        this.subject = subject
        this.teacher = teacher
        this.studentGroup = studentGroup
    }
    override fun toString(): String = subject
}
```

Note : Some uses cases have multiple planning entity classes.
Do not create unnecessary planning entity classes. This leads to difficult Move implementations and slower move evaluation.

__Important__

Planning entity `hashCode()` implementations must remain constant. Therefore entity `hashCode()` must not depend on any planning variables. Pay special attention when using data structures with auto-generated hashCode() as entities, such as Kotlin `data classes` or Lombok’s @EqualsAndHashCode.

### Planning Solution

This will be the class which will contain the whole solution and list of problem facts and everything. Everything is modelled under this class umbrella.

Designate the class as `PlanningSolution`
```kotlin
`@PlanningSolution`
class TimeTable{}
```

Provide the list of problem facts which are not in the Planning Entity. Provide all available timeslots and all available rooms.

```kotlin
@ProblemFactCollectionProperty
@ValueRangeProvider
lateinit var timeslots: List<Timeslot>

@ProblemFactCollectionProperty
@ValueRangeProvider
lateinit var rooms: List<Room>
```

- `@ValueRangeProvider` - Provides the planning values that can be used for a `PlanningVariable`.
- `@ProblemFactCollectionProperty` - Specifies that a property (or a field) on a `PlanningSolution` class is a Collection of problem facts. A problem fact must not change during solving (except through a `ProblemChange` event.

Provide the list of planning entities

```kotlin
@PlanningEntityCollectionProperty
lateinit var lessons: List<Lesson>
```

Provide planning score
```kotlin
@PlanningScore
var score: HardSoftScore? = null
```

Full class example
```kotlin
@PlanningSolution
class TT{
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    lateinit var timeslots: List<Timeslot>
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    lateinit var rooms: List<Room>
    @PlanningEntityCollectionProperty
    lateinit var lessons: List<Lesson>

    @PlanningScore
    var score: HardSoftScore? = null

    constructor() {}

    constructor(timeslots: List<Timeslot>, rooms: List<Room>, lessons: List<Lesson>) {
        this.timeslots = timeslots
        this.rooms = rooms
        this.lessons = lessons
    }

    override fun toString(): String {
        return "$timeslots $rooms $lessons"
    }

}
```

### Constraints
The constraints are modelled by providing an array of constraints via functions. Each function provides a score of HARD/SOFT positive or negative.

The constraint looks at what's there in the `PlanningSolution` e.g. could be attributes which are marked as `PlanningVariable` or not marked at all. Check unique combinations etc... and provide the score.

E.g.

```kotlin
fun teacherConflict(constraintFactory: ConstraintFactory): Constraint {
    // A teacher can teach at most one lesson at the same time.
    return constraintFactory
        .forEachUniquePair(
            Lesson::class.java,
            Joiners.equal(Lesson::timeslot),
            Joiners.equal(Lesson::teacher)
        )
        .penalize(HardSoftScore.ONE_HARD)
        .asConstraint("Teacher conflict");
}
```

Full class

```kotlin
class TTCP : ConstraintProvider {
    override fun defineConstraints(constraintFactory: ConstraintFactory): Array<out Constraint?> {
        return arrayOf(roomConflict(constraintFactory),
            teacherConflict(constraintFactory),
            studentGroupConflict(constraintFactory),
            teacherRoomStability(constraintFactory),
            teacherTimeEfficiency(constraintFactory),
            studentGroupSubjectVariety(constraintFactory))
    }

    fun roomConflict(cf: ConstraintFactory): Constraint {
        return cf.forEachUniquePair(
            Lesson::class.java,
            Joiners.equal(Lesson::timeslot),
            Joiners.equal(Lesson::room)
        ).penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Room Conflict")
    }

    fun teacherConflict(constraintFactory: ConstraintFactory): Constraint {
        // A teacher can teach at most one lesson at the same time.
        return constraintFactory
            .forEachUniquePair(
                Lesson::class.java,
                Joiners.equal(Lesson::timeslot),
                Joiners.equal(Lesson::teacher)
            )
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Teacher conflict");
    }
    fun studentGroupConflict(constraintFactory: ConstraintFactory): Constraint {
        // A student can attend at most one lesson at the same time.
        return constraintFactory
            .forEachUniquePair(
                Lesson::class.java,
                Joiners.equal(Lesson::timeslot),
                Joiners.equal(Lesson::studentGroup)
            )
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("Student group conflict");
    }

    fun teacherRoomStability(constraintFactory: ConstraintFactory): Constraint {
        // A teacher prefers to teach in a single room.
        return constraintFactory
            .forEachUniquePair(
                Lesson::class.java,
                Joiners.equal(Lesson::teacher)
            )
            .filter { lesson1: Lesson, lesson2: Lesson -> lesson1.room !== lesson2.room }
            .penalize(HardSoftScore.ONE_SOFT)
            .asConstraint("Teacher room stability");
    }
    fun teacherTimeEfficiency(constraintFactory: ConstraintFactory): Constraint {
        // A teacher prefers to teach sequential lessons and dislikes gaps between lessons.
        return constraintFactory
            .forEach(Lesson::class.java)
            .join(Lesson::class.java,
                Joiners.equal(Lesson::teacher),
                Joiners.equal { lesson: Lesson -> lesson.timeslot?.dayOfWeek })
            .filter { lesson1: Lesson, lesson2: Lesson ->
                val between = Duration.between(
                    lesson1.timeslot?.endTime,
                    lesson2.timeslot?.startTime
                )
                !between.isNegative && between.compareTo(Duration.ofMinutes(30)) <= 0
            }
            .reward(HardSoftScore.ONE_SOFT)
            .asConstraint("Teacher time efficiency");
    }

    fun studentGroupSubjectVariety(constraintFactory: ConstraintFactory): Constraint {
        // A student group dislikes sequential lessons on the same subject.
        return constraintFactory
            .forEach(Lesson::class.java)
            .join(Lesson::class.java,
                Joiners.equal(Lesson::subject),
                Joiners.equal(Lesson::studentGroup),
                Joiners.equal { lesson: Lesson -> lesson.timeslot?.dayOfWeek })
            .filter { lesson1: Lesson, lesson2: Lesson ->
                val between = Duration.between(
                    lesson1.timeslot?.endTime,
                    lesson2.timeslot?.startTime
                )
                !between.isNegative && between.compareTo(Duration.ofMinutes(30)) <= 0
            }
            .penalize(HardSoftScore.ONE_SOFT)
            .asConstraint("Student group subject variety");
    }

}
```

### Configure solver

The solver put's it all together and runs it.


```kotlin
val solverFactory: SolverFactory<TT> = SolverFactory.create(SolverConfig()
    .withSolutionClass(TT::class.java)
    .withEntityClasses(Lesson::class.java)
    .withConstraintProviderClass(TTCP::class.java)
    .withTerminationUnimprovedSpentLimit(Duration.ofMillis(400)))

val solver: Solver<TT> = solverFactory.buildSolver()

val solution: TT = solver.solve(problem)
```


# Models

```yaml
Person:
    min_gaurenteed_work: 37.5
    start_availability : 09:00
    end_availability : 18:00
    preferred_start_availability : 09:00
    preferred_end_availability : 18:00
    age : 19
    languages_spoken :
        - english
        - hindi
    address:
        postal_code:
        state:
    skills:
        - lead
        - employee
    min_time_between_shifts: 10hrs
    max_straight_working_days: 5
    avoid_shift_tags:
        - A
        - B
    rate: 100
    preferred_pairings:
        - PersonA
        - PersonB
    unpreferred_pairngs:
        - PersonC
        - PersonD
    required_pairings:
        - PersonA
        - PersonB
    prohibited_pairngs:
        - PersonC
        - PersonD
```

```yaml
Shift:
    id: 1
    skills:
        - A
        - B
    spoken_languages:
        - Hindi
        - English
    start_time : 10:00
    end_time : 17:00
```

```yaml
# Other Constraints
max_cost : 
preferred_max_cost : 
```


---

```json                                                                                          
{
  "config": {
    "run": {
      "name": "string",
      "termination": {
        "spentLimit": "P1D",
        "unimprovedSpentLimit": "P1D"
      },
      "maxThreadCount": 1,
      "tags": [
        "string"
      ]
    },
    "model": {
      "overrides": {
        "unassignedOptionalShiftWeight": 0,
        "minimizeTravelDistanceWeight": 0,
        "employeeAssignmentDisruptedOnReplanningWeight": 0,
        "preferredSkillMissingWeight": 0,
        "minutesWorkedPerPeriodNotInPreferredRangeForEmployeeWeight": 0,
        "minutesLoggedPerPeriodNotInPreferredRangeForEmployeeWeight": 0,
        "shiftsWorkedPerPeriodNotInPreferredRangeForEmployeeWeight": 0,
        "daysWorkedPerPeriodNotInPreferredRangeForEmployeeWeight": 0,
        "consecutiveDaysWorkedNotInPreferredRangeForEmployeeWeight": 0,
        "shiftTypesWorkedPerPeriodNotInPreferredRangeForEmployeeWeight": 0,
        "shiftsWorkedPerPeriodNotInPreferredRangeWeight": 0,
        "locationsWorkedPerPeriodNotInPreferredRangeForEmployeeWeight": 0,
        "minimizeGapsBetweenShiftsForEmployeeWeight": 0,
        "shiftsWorkedNotInPreferredHourlyDemandRangeWeight": 0,
        "balanceShiftsWorkedForMinimumHourlyDemandWeight": 0,
        "costsPerPeriodNotInPreferredRangeWeight": 0,
        "balanceShiftCountWeight": 0,
        "balanceTimeWorkedWeight": 0,
        "employeeWorksDuringPreferredTimeWeight": 0,
        "employeeIsPairedWithPreferredEmployeeWeight": 0,
        "employeeDoesNotHavePreferredDailyShiftPairingWeight": 0,
        "employeeHasUnpreferredDailyShiftPairingWeight": 0,
        "employeeWorksDuringUnpreferredTimeWeight": 0,
        "employeeWorksShiftWithPreferredShiftTagsWeight": 0,
        "employeeHasUnpreferredShiftNearDayOffRequestWeight": 0,
        "minutesBetweenShiftsNotInPreferredRangeForEmployeeWeight": 0,
        "preferredEmployeeAssignedWeight": 0,
        "unpreferredEmployeeAssignedWeight": 0,
        "employeeIsPairedWithUnpreferredEmployeeWeight": 0,
        "employeeWorksPreferredSingleDayShiftSequencePatternWeight": 0,
        "employeeWorksUnpreferredSingleDayShiftSequencePatternWeight": 0,
        "employeeWorksPreferredMultiDayShiftSequencePatternWeight": 0,
        "employeeWorksUnpreferredMultiDayShiftSequencePatternWeight": 0,
        "uniqueTagsPerPeriodNotInPreferredRangeForEmployee": 0
      }
    }
  },
  "modelInput": {
    "tagTypes": [
      {
        "id": "string"
      }
    ],
    "tags": [
      {
        "id": "string",
        "tagType": "string"
      }
    ],
    "contracts": [
      {
        "id": "string",
        "priority": "10",
        "consecutiveDaysWorkedRules": [
          {
            "id": "string",
            "minimum": 0,
            "maximum": 0,
            "satisfiability": "REQUIRED",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL",
            "shiftTypeTagCategories": [
              "string"
            ]
          }
        ],
        "periodRules": [
          {
            "id": "string",
            "period": "string",
            "ruleValidityDateTimeSpan": {
              "start": "2022-03-10T12:15:50",
              "end": "2022-03-10T12:15:50"
            },
            "satisfiability": "REQUIRED",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL",
            "minutesWorkedMin": 0,
            "minutesWorkedMax": 0,
            "minutesLoggedMin": 0,
            "minutesLoggedMax": 0,
            "shiftsWorkedMin": 0,
            "shiftsWorkedMax": 0,
            "daysWorkedMin": 0,
            "daysWorkedMax": 0,
            "shiftTypesTagCategories": [
              "string"
            ],
            "shiftTypesWorkedMin": 0,
            "shiftTypesWorkedMax": 0,
            "locationsWorkedMax": 0,
            "uniqueTagsLimit": {
              "tagsMax": 1,
              "includeTagTypes": [
                "string"
              ]
            },
            "periodShiftOverlapKind": "START_ONLY"
          }
        ],
        "avoidShiftCloseToDayOffRequestRules": [
          {
            "id": "string",
            "avoidPriorShiftTags": [
              "string"
            ],
            "avoidAfterShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL",
            "satisfiability": "PROHIBITED"
          }
        ],
        "minutesBetweenShiftsRules": [
          {
            "id": "string",
            "requiredPriorShiftTags": [
              "string"
            ],
            "minimumConsecutivePriorShifts": 1,
            "requiredAfterShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL",
            "satisfiability": "REQUIRED",
            "minimumMinutesBetweenShifts": 0,
            "maximumMinutesBetweenShifts": 0,
            "scope": {
              "duration": "string"
            }
          }
        ],
        "minimizeGapsBetweenShiftsRules": [
          {
            "id": "string",
            "requiredPriorShiftTags": [
              "string"
            ],
            "requiredAfterShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL",
            "maximumMinutesBetweenSplitShifts": 120
          }
        ],
        "dailyShiftPairingRules": [
          {
            "id": "string",
            "shiftTags": [
              "string"
            ],
            "pairedShiftTags": [
              "string"
            ],
            "dayOffset": 0,
            "shiftTagMatches": "ALL",
            "satisfiability": "REQUIRED"
          }
        ],
        "allowOverlappingShiftsRules": [
          {
            "id": "string",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL"
          }
        ],
        "travelConfigurations": [
          {
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL",
            "excludeMatchingShiftTagTypes": [
              "string"
            ],
            "maxEmployeeToShiftTravelDistanceInMeters": 0,
            "minMinutesBetweenShiftsInDifferentLocations": 0,
            "id": "string",
            "validityDateTimeSpan": {
              "start": "2022-03-10T12:15:50",
              "end": "2022-03-10T12:15:50"
            }
          }
        ],
        "singleDayShiftSequencePatternRules": [
          {
            "id": "string",
            "satisfiability": "REQUIRED",
            "weight": 1,
            "pattern": [
              {
                "includeShiftTags": [
                  "string"
                ],
                "excludeShiftTags": [
                  "string"
                ],
                "shiftTagMatches": "ALL"
              }
            ],
            "ordered": true
          }
        ],
        "multiDayShiftSequencePatternRules": [
          {
            "id": "string",
            "satisfiability": "REQUIRED",
            "weight": 1,
            "pattern": [
              {
                "type": "ON",
                "includeShiftTags": [
                  "string"
                ],
                "excludeShiftTags": [
                  "string"
                ],
                "shiftTagMatches": "ALL",
                "shiftMatches": "ALL"
              }
            ]
          }
        ]
      }
    ],
    "employees": [
      {
        "id": "string",
        "contracts": [
          "string"
        ],
        "priority": "CRITICAL",
        "costGroup": "string",
        "location": [
          40.5044403760272,
          -76.37894009358867
        ],
        "skills": [
          {
            "id": "string",
            "validityDateTimeSpans": [
              {
                "start": "2022-03-10T12:15:50-04:00",
                "end": "2022-03-10T12:15:50-04:00"
              }
            ]
          }
        ],
        "tags": [
          "string"
        ],
        "preferredShiftTags": [
          "string"
        ],
        "requiredShiftTags": [
          "string"
        ],
        "prohibitedRiskFactors": [
          "string"
        ],
        "preferredPairings": [
          {
            "pairedEmployee": "string",
            "onlyForShiftTags": [
              "string"
            ]
          }
        ],
        "unpreferredPairings": [
          {
            "pairedEmployee": "string",
            "onlyForShiftTags": [
              "string"
            ]
          }
        ],
        "requiredPairings": [
          {
            "pairedEmployee": "string",
            "onlyForShiftTags": [
              "string"
            ]
          }
        ],
        "prohibitedPairings": [
          {
            "pairedEmployee": "string",
            "onlyForShiftTags": [
              "string"
            ]
          }
        ],
        "preferredTimeSpans": [
          {
            "start": "2022-03-10T12:15:50-04:00",
            "end": "2022-03-10T12:15:50-04:00",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL"
          }
        ],
        "unpreferredTimeSpans": [
          {
            "start": "2022-03-10T12:15:50-04:00",
            "end": "2022-03-10T12:15:50-04:00",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL"
          }
        ],
        "unavailableTimeSpans": [
          {
            "start": "2022-03-10T12:15:50-04:00",
            "end": "2022-03-10T12:15:50-04:00",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL"
          }
        ],
        "availableTimeSpans": [
          {
            "start": "2022-03-10T12:15:50-04:00",
            "end": "2022-03-10T12:15:50-04:00",
            "includeShiftTags": [
              "string"
            ],
            "excludeShiftTags": [
              "string"
            ],
            "shiftTagMatches": "ALL"
          }
        ],
        "timeZoneId": "Z"
      }
    ],
    "shifts": [
      {
        "id": "string",
        "start": "2022-03-10T12:15:50-04:00",
        "end": "2022-03-10T12:15:50-04:00",
        "loggedTime": "P1D",
        "costGroup": "string",
        "location": [
          40.5044403760272,
          -76.37894009358867
        ],
        "requiredSkills": [
          "string"
        ],
        "preferredSkills": [
          "string"
        ],
        "riskFactors": [
          "string"
        ],
        "tags": [
          "string"
        ],
        "preferredEmployees": [
          "string"
        ],
        "unpreferredEmployees": [
          "string"
        ],
        "prohibitedEmployees": [
          "string"
        ],
        "priority": "1",
        "pinned": false,
        "employee": "string"
      }
    ],
    "shiftGroups": [
      {
        "id": "string",
        "tags": [
          "string"
        ],
        "shifts": [
          "string"
        ]
      }
    ],
    "scheduleParameterization": {
      "weekStart": "MONDAY",
      "periods": [
        {
          "id": "string",
          "dateSpans": [
            {
              "start": "2022-03-10",
              "end": "2022-03-10"
            }
          ]
        }
      ]
    },
    "globalRules": {
      "balanceTimeWorkedRules": [
        {
          "id": "string",
          "includeEmployeeTags": [
            "string"
          ],
          "excludeEmployeeTags": [
            "string"
          ],
          "employeeTagMatches": "ALL",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "employeeToPublishedMinutesWorked": {
            "additionalProp1": 0,
            "additionalProp2": 0,
            "additionalProp3": 0
          }
        }
      ],
      "balanceShiftCountRules": [
        {
          "id": "string",
          "includeEmployeeTags": [
            "string"
          ],
          "excludeEmployeeTags": [
            "string"
          ],
          "employeeTagMatches": "ALL",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "employeeToPublishedShiftCount": {
            "additionalProp1": 0,
            "additionalProp2": 0,
            "additionalProp3": 0
          }
        }
      ],
      "minimumMaximumShiftsPerPeriod": [
        {
          "id": "string",
          "period": "string",
          "zoneOffset": "string",
          "satisfiability": "REQUIRED",
          "includeEmployeeTags": [
            "string"
          ],
          "excludeEmployeeTags": [
            "string"
          ],
          "employeeTagMatches": "ALL",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "shiftsWorkedMin": 0,
          "shiftsWorkedMax": 0,
          "periodShiftOverlapKind": "START_ONLY"
        }
      ],
      "minimumMaximumShiftsPerHourlyDemand": [
        {
          "id": "string",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "satisfiability": "REQUIRED",
          "demandDetails": [
            {
              "startDateTime": "2022-03-10T12:15:50-04:00",
              "minDemand": 0,
              "maxDemand": 0,
              "rule": {
                "id": "string",
                "includeShiftTags": [
                  "string"
                ],
                "excludeShiftTags": [
                  "string"
                ],
                "shiftTagMatches": "ALL",
                "satisfiability": "REQUIRED",
                "demandDetails": [
                  {
                    "startDateTime": "2022-03-10T12:15:50-04:00",
                    "minDemand": 0,
                    "maxDemand": 0,
                    "rule": "string"
                  }
                ]
              }
            }
          ]
        }
      ],
      "costsRules": [
        {
          "id": "string",
          "period": "string",
          "zoneOffset": "Z",
          "includeEmployeeTags": [
            "string"
          ],
          "excludeEmployeeTags": [
            "string"
          ],
          "employeeTagMatches": "ALL",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "satisfiability": "REQUIRED",
          "employeeShiftCostDetails": [
            {
              "employeeCostGroup": "string",
              "shiftCostGroup": "string",
              "cost": 0
            }
          ],
          "totalCostsMin": 0,
          "totalCostsMax": 0,
          "periodShiftOverlapKind": "START_ONLY",
          "costsRuleShiftOverlapKind": "START_ONLY"
        }
      ],
      "disruptionRules": [
        {
          "id": "string",
          "start": "2022-03-10",
          "end": "2022-03-10",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "multiplier": 1
        }
      ],
      "shiftTagMatchRules": [
        {
          "id": "string",
          "satisfiability": "REQUIRED",
          "tagTypeMatchMultipliers": {
            "additionalProp1": 0,
            "additionalProp2": 0,
            "additionalProp3": 0
          }
        }
      ],
      "unassignedShiftRule": {
        "id": "string",
        "priorityWeights": [
          {
            "priority": "string",
            "weight": 0,
            "assignment": "MANDATORY"
          }
        ]
      },
      "shiftGroupRules": [
        {
          "id": "string",
          "includeShiftGroupTags": [
            "string"
          ],
          "assignmentMode": {
            "type": "SINGLE_SHIFT"
          }
        }
      ],
      "concurrentShiftsRules": [
        {
          "id": "string",
          "includeShiftTags": [
            "string"
          ],
          "excludeShiftTags": [
            "string"
          ],
          "shiftTagMatches": "ALL",
          "ruleValidityDateTimeSpan": {
            "start": "2022-03-10T12:15:50",
            "end": "2022-03-10T12:15:50"
          },
          "timeZoneId": "Z",
          "concurrentShiftsMax": 0
        }
      ]
    },
    "planningWindow": {
      "start": "2022-03-10T12:15:50-04:00",
      "end": "2022-03-10T12:15:50-04:00"
    }
  }
}
```



---

Kotlin higher order functions 

```kotlin
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore
import ai.timefold.solver.core.api.score.stream.Constraint
import ai.timefold.solver.core.api.score.stream.ConstraintFactory
import ai.timefold.solver.core.api.score.stream.Joiners

data class Shift(val id: Int) {}

enum class Rules{
    UniqueEmployeePerShift,
    EmployeeAvailability
}


fun uniqueEmployeePerShift(constraintFactory: ConstraintFactory): Constraint {
    return constraintFactory.forEachUniquePair(
        Shift::class.java,
        Joiners.equal(Shift::id),
        Joiners.equal(Shift::id)
    )
        .penalize(HardSoftScore.ONE_HARD)
        .asConstraint("uniqueEmployeePerShift")
}


fun employeeAvailability(constraintFactory: ConstraintFactory): Constraint {
    return constraintFactory.forEachUniquePair(
        Shift::class.java,
        Joiners.equal(Shift::id),
        Joiners.equal(Shift::id)
    )
        .penalize(HardSoftScore.ONE_HARD)
        .asConstraint("uniqueEmployeePerShift")
}


fun main() {
    val mapping = hashMapOf<Rules, (cf: ConstraintFactory)-> Constraint>()
    mapping.put(Rules.UniqueEmployeePerShift,  ::uniqueEmployeePerShift)
}
```