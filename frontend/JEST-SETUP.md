# Jest Testing Framework Setup for Frontend

## Overview

WebSmartStorage frontend uses **Jest** as the testing framework (replacing Karma/Jasmine).

**Why Jest?**
- ✅ Fast parallel test execution
- ✅ Great Angular 22 integration with `jest-preset-angular`
- ✅ Simple configuration
- ✅ Excellent mocking and spying capabilities
- ✅ Better for component/unit testing workflows

## Configuration Files

### `jest.config.js` (root of `frontend/`)

Main Jest configuration:
- Uses `jest-preset-angular` preset
- Configures TypeScript transformation via `ts-jest`
- Ignores `node_modules` and `dist`
- Transforms HTML and SVG as strings
- Sets up jsdom test environment for DOM testing

### `setup-jest.ts` (root of `frontend/`)

Jest initialization file (runs before all tests):
- Initializes `jest-preset-angular` setup
- Polyfills DOM APIs (window.CSS, getComputedStyle, etc.)
- Required for Angular component testing

### `tsconfig.spec.json` (root of `frontend/`)

TypeScript configuration specific to tests:
- Extends base `tsconfig.json`
- Includes `jest` types
- Compiles spec files as CommonJS for Jest

## Running Tests

```bash
# Run all tests once
npm test

# Run with coverage report
npm test -- --coverage

# Run in watch mode (re-run on file change)
npm test -- --watch

# Run specific test file
npm test -- menu.component.spec.ts

# Run tests matching pattern
npm test -- --testNamePattern="should render"
```

## Test File Structure

**Location:** Co-located with components
```
frontend/src/app/
  core/
    layout/
      menu.component.ts
      menu.component.html
      menu.component.scss
      menu.component.spec.ts      ← Test file here
    config/
      menu.service.ts
      menu.service.spec.ts        ← Service test
```

**File naming:** `*.spec.ts` (Angular convention)

## Component Test Template

```typescript
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MenuComponent } from './menu.component';
import { MenuService } from '../../core/config/menu.service';
import { signal } from '@angular/core';

describe('MenuComponent', () => {
  let component: MenuComponent;
  let fixture: ComponentFixture<MenuComponent>;
  let mockMenuService: any;

  beforeEach(async () => {
    // Create mock service
    mockMenuService = {
      menuGroups: jest.fn().mockReturnValue(signal([{
        id: 'base',
        label: 'Base Data',
        icon: 'folder-outline',
        items: []
      }])),
      isLoading: jest.fn().mockReturnValue(signal(false)),
      error: jest.fn().mockReturnValue(signal(null))
    };

    // Configure testing module
    await TestBed.configureTestingModule({
      imports: [MenuComponent],
      providers: [
        { provide: MenuService, useValue: mockMenuService }
      ]
    }).compileComponents();

    // Create component instance
    fixture = TestBed.createComponent(MenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render ion-menu element', () => {
    const menu = fixture.nativeElement.querySelector('ion-menu');
    expect(menu).toBeTruthy();
  });

  it('should have when="lg" for responsive behavior', () => {
    const menu = fixture.nativeElement.querySelector('ion-menu');
    expect(menu.getAttribute('when')).toBe('lg');
  });

  it('should display menu groups from service', () => {
    const groups = fixture.nativeElement.querySelectorAll('ion-accordion');
    expect(groups.length).toBe(1);
  });
});
```

## Service Test Template

```typescript
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MenuService } from './menu.service';

describe('MenuService', () => {
  let service: MenuService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MenuService]
    });

    service = TestBed.inject(MenuService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should load menu config from JSON', (done) => {
    service.loadMenuConfig().then(() => {
      expect(service.menuGroups().length).toBeGreaterThan(0);
      done();
    });

    const req = httpMock.expectOne('/assets/config/menu.json');
    expect(req.request.method).toBe('GET');
    req.flush({
      menuGroups: [
        { id: 'base', label: 'Base Data', items: [] }
      ]
    });
  });
});
```

## Mandatory Test Coverage

### Per Component
- ✅ Component instantiation
- ✅ Template rendering (elements present)
- ✅ Input/Output bindings
- ✅ Event handlers
- ✅ Service integration

### Per Service
- ✅ Service instantiation
- ✅ Method returns correct values
- ✅ HTTP calls (if applicable)
- ✅ Signal state updates
- ✅ Error handling

### Coverage Target
- **New code:** >80% line coverage
- **Run coverage:** `npm test -- --coverage`
- **Report location:** `coverage/` directory (auto-generated)

## Common Jest Matchers

```typescript
// Basic matchers
expect(value).toBeTruthy();
expect(value).toBeFalsy();
expect(value).toBeNull();
expect(value).toBeDefined();
expect(value).toBeUndefined();

// Equality
expect(value).toBe(5);               // ===
expect(value).toEqual({ a: 1 });    // Deep equality

// Collections
expect(array).toContain('item');
expect(array).toHaveLength(3);

// Exceptions
expect(() => fn()).toThrow();
expect(() => fn()).toThrow('error message');

// Mocks & Spies
expect(mockFn).toHaveBeenCalled();
expect(mockFn).toHaveBeenCalledWith(arg);
expect(mockFn).toHaveBeenCalledTimes(3);

// Async
jest.useFakeTimers();
jest.advanceTimersByTime(1000);
jest.useRealTimers();
```

## Testing Ionic Components

When testing Ionic components (ion-menu, ion-item, etc.):

```typescript
it('should work with Ionic components', () => {
  // Query Ionic elements
  const ionMenu = fixture.nativeElement.querySelector('ion-menu');
  const ionItems = fixture.nativeElement.querySelectorAll('ion-item');

  // Check attributes
  expect(ionMenu.getAttribute('side')).toBe('start');
  expect(ionMenu.getAttribute('when')).toBe('lg');

  // Check content via text
  expect(ionItems[0].textContent).toContain('Countries');
});
```

## Mocking Services with Signals

```typescript
// Mock service with Signals
const mockMenuService = {
  menuGroups: jest.fn().mockReturnValue(signal([
    {
      id: 'base',
      label: 'Base Data',
      icon: 'folder-outline',
      items: [{ id: 'countries', label: 'Countries', route: '/countries', icon: 'globe-outline' }]
    }
  ])),
  isLoading: jest.fn().mockReturnValue(signal(false)),
  error: jest.fn().mockReturnValue(signal(null))
};

// In TestBed
TestBed.configureTestingModule({
  imports: [MenuComponent],
  providers: [{ provide: MenuService, useValue: mockMenuService }]
});
```

## Debugging Tests

```typescript
// Print component HTML to console
console.log(fixture.debugElement.nativeElement.innerHTML);

// Use fdescribe/fit to run only specific tests
fdescribe('MenuComponent', () => { ... });  // Run only this suite
fit('should...', () => { ... });            // Run only this test

// Use skip to exclude tests temporarily
xdescribe('MenuComponent', () => { ... });  // Skip entire suite
xit('should...', () => { ... });            // Skip single test
```

## Troubleshooting

### "Cannot find module '@ionic/angular'"
- Check `jest.config.js` has `transformIgnorePatterns` for `node_modules/(?!@ionic)`
- Verify `@ionic/angular` is installed: `npm ls @ionic/angular`

### "Test environment jsdom cannot be found"
- Install: `npm install --save-dev jest-environment-jsdom`
- Ensure `jest.config.js` has `testEnvironment: 'jsdom'`

### "Signal is not a function"
- Import from `@angular/core`: `import { signal } from '@angular/core'`
- Mock service Signals properly in tests (see example above)

## Troubleshooting

### "Must use import to load ES Module" errors

**Problem:** Jest can't load Angular/Ionic ESM modules

**Solution:** 
- Add `esModuleInterop: true` and `allowSyntheticDefaultImports: true` to `tsconfig.spec.json`
- Set `useESM: true` in jest-preset-angular transform config
- Add `extensionsToTreatAsEsm: ['.ts']` to jest.config.js

### "Cannot find module 'ionicons/...'" errors

**Problem:** ionicons ESM imports fail in tests

**Solution:** Already handled by transform ignore patterns. If issues persist, add to jest.config.js:
```javascript
transformIgnorePatterns: [
  'node_modules/(?!(@angular|@ionic|ionicons)/)'
]
```

### "Unexpected standalone component in declarations" error

**Problem:** Using `declarations: [Component]` for standalone components (NgModule pattern)

**Solution:** Use `imports: [Component]` instead and remove `declarations` array entirely.

**Before:**
```typescript
TestBed.configureTestingModule({
  declarations: [AppComponent],
  imports: [IonicModule.forRoot()]
}).compileComponents();
```

**After (modern Angular 22):**
```typescript
TestBed.configureTestingModule({
  imports: [AppComponent],
  providers: [provideIonicAngular()]
}).compileComponents();
```

### "[Ionic Error]: [ion-menu] - Must have a 'content' element"

**Problem:** Ionic components in tests require proper DOM structure

**Solution:** This is expected when testing in isolation. Either:
1. Mock the component and test in isolation
2. Add `CUSTOM_ELEMENTS_SCHEMA` to suppress errors
3. Simplify tests to check attributes instead of internal rendering

### "window.matchMedia is not a function" error

**Problem:** Ionic components use window.matchMedia which doesn't exist in Jest test environment

**Solution:** Add polyfill to `setup-jest.ts`:
```typescript
Object.defineProperty(window, 'matchMedia', {
  writable: true,
  value: jest.fn().mockImplementation(query => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: jest.fn(),
    removeListener: jest.fn(),
    addEventListener: jest.fn(),
    removeEventListener: jest.fn(),
    dispatchEvent: jest.fn(),
  })),
});
```

### "IonicModule has been deprecated" warning

**Problem:** Tests using old `IonicModule.forRoot()` pattern trigger deprecation warning

**Solution:** Replace with modern standalone pattern:

**Before:**
```typescript
imports: [IonicModule.forRoot(), RouterModule.forRoot([])]
```

**After:**
```typescript
imports: [RouterModule.forRoot([])],
providers: [provideIonicAngular()]
```

### "Need to call TestBed.initTestEnvironment() first"

**Problem:** TestBed not initialized when tests run

**Solution:** Ensure `setup-jest.ts` calls:
```typescript
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);
```

This is now handled automatically in the Jest setup.

---

**Last updated:** 2026-09-16  
**Jest version:** 29+  
**Angular:** 22  
**Ionic:** 9  
**Status:** ✅ All tests passing, full coverage reporting working

For GitHub Actions or other CI:

```yaml
- name: Run tests
  run: npm test -- --coverage --ci --maxWorkers=2

- name: Build
  run: npm run build
```

## Resources

- [Jest Documentation](https://jestjs.io/)
- [jest-preset-angular](https://github.com/thymikee/jest-preset-angular)
- [Angular Testing Guide](https://angular.io/guide/testing)
- [TestBed API](https://angular.io/api/core/testing/TestBed)

---

**Last updated:** 2026-09-15  
**Jest version:** 29+  
**Angular:** 22  
**Ionic:** 9
