package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.controller.dto.EnrollmentInput;
import be.ucll.se.courses.backend.controller.dto.ScheduleInput;
import be.ucll.se.courses.backend.model.Schedule;
import be.ucll.se.courses.backend.service.ScheduleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * Get the schedule of a lecturer or if the user is an admin, a list of all schedules.
     * Other users: AccessDeniedException
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    public List<Schedule> getSchedules() {
        return scheduleService.getSchedules();
    }

    @PostMapping
    @ApiResponse(responseCode = "200", description = "Create a schedule")
    @Parameter(name = "schedule_input", description = "A new schedule object")
    public Schedule createSchedule(@RequestBody ScheduleInput schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @PostMapping("/enroll")
    public Schedule enroll(@RequestBody EnrollmentInput enrollmentInput) {
        return scheduleService.enroll(enrollmentInput);
    }
}
