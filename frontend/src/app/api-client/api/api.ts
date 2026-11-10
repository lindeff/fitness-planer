export * from './exercises.service';
import { ExercisesService } from './exercises.service';
export * from './workoutPlans.service';
import { WorkoutPlansService } from './workoutPlans.service';
export * from './workoutSessions.service';
import { WorkoutSessionsService } from './workoutSessions.service';
export const APIS = [ExercisesService, WorkoutPlansService, WorkoutSessionsService];
