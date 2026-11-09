/** Muscle group of an exercise (matches the backend's MuscleGroup enum / openapi.yaml). */
export type MuscleGroup =
  | 'CHEST'
  | 'BACK'
  | 'SHOULDERS'
  | 'BICEPS'
  | 'TRICEPS'
  | 'LEGS'
  | 'CORE'
  | 'FULL_BODY';

/** An exercise from the predefined catalog (GET /exercises). */
export interface Exercise {
  id: string;
  name: string;
  muscleGroup?: MuscleGroup | null;
  sets?: number | null;
  targetRepetitions?: number | null;
}
