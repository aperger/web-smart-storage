import { PreloadAllModules, Route } from '@angular/router';

export const routes: Route[] = [
  {
    path: '',
    redirectTo: 'folder/Inbox',
    pathMatch: 'full'
  },
  {
    path: 'folder/:folder',
    loadChildren: () => import('./folder/folder.module').then( m => m.FolderPageModule)
  }
];

