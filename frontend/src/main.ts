import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, RouteReuseStrategy, withPreloading, PreloadAllModules } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { IonicRouteStrategy, provideIonicAngular } from '@ionic/angular';

import { AppComponent } from './app/app.component';
import { routes } from './app/app-routing.module';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter(routes, withPreloading(PreloadAllModules)),
    provideIonicAngular(),
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ]
}).catch(err => console.log(err));



