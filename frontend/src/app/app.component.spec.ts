import { CUSTOM_ELEMENTS_SCHEMA, signal } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { provideIonicAngular } from '@ionic/angular';

import { AppComponent } from './app.component';
import { MenuService } from './core/config/menu.service';

describe('AppComponent', () => {
  const mockMenuService = {
    menuGroups: signal([]),
    isLoading: signal(false),
    error: signal(null)
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent, RouterModule.forRoot([])],
      providers: [
        provideIonicAngular(),
        { provide: MenuService, useValue: mockMenuService }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it.skip('should have menu labels', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const app = fixture.nativeElement;
    const menuItems = app.querySelectorAll('ion-label');
    expect(menuItems.length).toEqual(12);
    expect(menuItems[0].innerHTML).toContain('Inbox');
    expect(menuItems[1].innerHTML).toContain('Outbox');
  });

  it.skip('should have urls', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const app = fixture.nativeElement;
    expect(app.querySelectorAll('ion-item').length).toEqual(12);
    const router = TestBed.inject(Router);
    const links = fixture.debugElement
      .queryAll(By.directive(RouterLink))
      .map((el) => el.injector.get(RouterLink));
    expect(links.length).toEqual(6);
    expect(router.serializeUrl(links[0].urlTree!)).toEqual('/folder/Inbox');
    expect(router.serializeUrl(links[1].urlTree!)).toEqual('/folder/Outbox');
  });
});
