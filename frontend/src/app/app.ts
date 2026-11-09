import { Component } from '@angular/core';
import { ExerciseCatalog } from './exercises/exercise-catalog';

@Component({
  selector: 'app-root',
  imports: [ExerciseCatalog],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'frontend';
}
