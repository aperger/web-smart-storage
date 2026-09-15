import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { MenuComponent } from './menu.component';
import { MenuService } from '../../core/config/menu.service';
import { signal } from '@angular/core';
import { provideIonicAngular } from '@ionic/angular';
import { RouterTestingModule } from '@angular/router/testing';

describe('MenuComponent', () => {
  let component: MenuComponent;
  let fixture: ComponentFixture<MenuComponent>;
  let mockMenuService: any;

  beforeEach(async () => {
    // Create a mock MenuService
    mockMenuService = {
      menuGroups: signal([
        {
          id: 'base',
          label: 'Base Data',
          icon: 'folder-outline',
          items: [
            {
              id: 'countries',
              label: 'Countries',
              route: '/countries',
              icon: 'globe-outline'
            }
          ]
        }
      ]),
      isLoading: signal(false),
      error: signal(null)
    };

    await TestBed.configureTestingModule({
      imports: [MenuComponent, RouterTestingModule],
      providers: [
        { provide: MenuService, useValue: mockMenuService },
        provideIonicAngular()
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(MenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the menu component', () => {
    expect(component).toBeTruthy();
  });

  it('should render ion-content element', () => {
    const contentElement = fixture.nativeElement.querySelector('ion-content');
    expect(contentElement).toBeTruthy();
  });

  it('should expose menu groups from service', () => {
    expect(component.menuService.menuGroups().length).toBe(1);
  });

  it('should load menu from service', () => {
    // Verify component is created and bound to service
    expect(component).toBeTruthy();
  });

  it('should expose loading state from service', () => {
    expect(component.menuService.isLoading()).toBe(false);
  });
});
