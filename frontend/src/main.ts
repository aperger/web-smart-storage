import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, RouteReuseStrategy, withPreloading, PreloadAllModules, withComponentInputBinding } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { IonicRouteStrategy, provideIonicAngular } from '@ionic/angular';
import { addIcons } from 'ionicons';
import {
  folderOutline,
  globeOutline,
  receiptOutline,
  cashOutline,
  layersOutline,
  listOutline,
  cardOutline,
  peopleOutline,
  bookmarkOutline,
  documentOutline,
  documentTextOutline,
  cloudUploadOutline,
  settings,
  personCircleOutline,
  settingsOutline,
  logOutOutline,
  menuOutline,
  closeOutline
} from 'ionicons/icons';

import { AppComponent } from './app/app.component';
import { routes } from './app/app-routing.module';

// Register Ionicons for use in templates
addIcons({
  'folder-outline': folderOutline,
  'globe-outline': globeOutline,
  'receipt-outline': receiptOutline,
  'cash-outline': cashOutline,
  'layers-outline': layersOutline,
  'list-outline': listOutline,
  'card-outline': cardOutline,
  'people-outline': peopleOutline,
  'bookmark-outline': bookmarkOutline,
  'document-outline': documentOutline,
  'document-text-outline': documentTextOutline,
  'cloud-upload-outline': cloudUploadOutline,
  'settings': settings,
  'person-circle-outline': personCircleOutline,
  'settings-outline': settingsOutline,
  'log-out-outline': logOutOutline,
  'menu-outline': menuOutline,
  'close-outline': closeOutline
});

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter(routes, withPreloading(PreloadAllModules), withComponentInputBinding()),
    provideIonicAngular(),
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ]
}).catch(err => console.log(err));



