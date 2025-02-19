import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SensorListComponent } from './sensor-list/sensor-list.component';
import { SensorFormComponent } from './sensor-form/sensor-form.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NgCircleProgressModule} from 'ng-circle-progress';
import { SensorCardsComponent } from './sensor-cards/sensor-cards.component';

@NgModule({
  declarations: [
    AppComponent,
    SensorListComponent,
    SensorFormComponent,
    DashboardComponent,
    SensorCardsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgCircleProgressModule.forRoot({})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
