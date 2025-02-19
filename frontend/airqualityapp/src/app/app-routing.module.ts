import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SensorListComponent } from './sensor-list/sensor-list.component';
import { SensorFormComponent } from './sensor-form/sensor-form.component';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent},
  { path: 'add-reading', component: SensorFormComponent},
  { path: 'reading', component: SensorListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
