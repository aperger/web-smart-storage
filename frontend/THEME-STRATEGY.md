# WebSmartStorage Frontend — Theme Color Strategy

## Goal

Implement light and dark themes using only CSS custom properties (variables), with zero per-component customization. Themes are switched globally via a single body class.

## Color Palettes (from logo analysis)

### Light Theme (Blue-dominant)

```scss
body.theme-light {
  // Primary (Blue from logo)
  --ion-color-primary:            #2F71A2;  // Logo Blue
  --ion-color-primary-contrast:   #FFFFFF;
  --ion-color-primary-shade:      #1D4766;  // Darker blue (hover/active)
  --ion-color-primary-tint:       #5097CC;  // Lighter blue (disabled)

  // Secondary (Orange from logo)
  --ion-color-secondary:          #FF9933;  // Logo Orange (highlights, badges)
  --ion-color-secondary-contrast: #FFFFFF;
  --ion-color-secondary-shade:    #E57200;
  --ion-color-secondary-tint:     #FFBF7F;

  // Status colors
  --ion-color-success:            #10DC60;
  --ion-color-warning:            #FFCE00;
  --ion-color-danger:             #F04141;

  // Background & text
  --ion-background-color:         #FFFFFF;
  --ion-text-color:               #000000;
  --ion-border-color:             #CCCCCC;

  // Neutrals
  --ion-color-light:              #F4F5F8;
  --ion-color-medium:             #989AA2;
  --ion-color-dark:               #222428;
}
```

### Dark Theme (Orange-primary, Blue-secondary)

```scss
body.theme-dark {
  // Primary (Orange from logo - high contrast on dark)
  --ion-color-primary:            #FF9933;  // Logo Orange (action buttons, links)
  --ion-color-primary-contrast:   #000000;
  --ion-color-primary-shade:      #E57200;  // Darker orange (hover/active)
  --ion-color-primary-tint:       #FFAD5B;  // Lighter orange

  // Secondary (Blue from logo - info/secondary actions)
  --ion-color-secondary:          #2F71A2;  // Logo Blue (info, secondary actions)
  --ion-color-secondary-contrast: #FFFFFF;
  --ion-color-secondary-shade:    #1D4766;
  --ion-color-secondary-tint:     #5097CC;

  // Status colors (adjusted for dark)
  --ion-color-success:            #2FDF75;
  --ion-color-warning:            #FFD534;
  --ion-color-danger:             #FF6B6B;

  // Background & text
  --ion-background-color:         #1A1A1A;
  --ion-text-color:               #E8E8E8;
  --ion-border-color:             #444444;

  // Neutrals
  --ion-color-light:              #3A3A3A;
  --ion-color-medium:             #888888;
  --ion-color-dark:               #F4F5F8;
}
```

## Implementation Structure

### File Organization

```text
src/
  app/
    core/
      theme/
        theme.service.ts           // Service managing theme state & persistence
        theme.model.ts             // ThemeOption enum/types
  assets/
    styles/
      variables/
        colors-light.scss          // Light theme color definitions
        colors-dark.scss           // Dark theme color definitions
      theme.scss                   // Theme switching logic (imports both)
```

### Step 1: Define Theme Service

**`src/app/core/theme/theme.model.ts`**

```typescript
export type ThemeOption = 'light' | 'dark';

export interface ThemeConfig {
  current: ThemeOption;
  available: ThemeOption[];
}
```

**`src/app/core/theme/theme.service.ts`**

```typescript
import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private readonly STORAGE_KEY = 'app-theme-preference';
  private readonly DEFAULT_THEME: ThemeOption = 'light';

  currentTheme = signal<ThemeOption>(this.DEFAULT_THEME);

  constructor() {
    this.initializeTheme();
  }

  private initializeTheme(): void {
    const savedTheme = this.getSavedTheme();
    this.setTheme(savedTheme);
  }

  private getSavedTheme(): ThemeOption {
    const saved = localStorage.getItem(this.STORAGE_KEY) as ThemeOption | null;
    return saved && (saved === 'light' || saved === 'dark') ? saved : this.DEFAULT_THEME;
  }

  setTheme(theme: ThemeOption): void {
    this.currentTheme.set(theme);
    this.applyThemeToDOM(theme);
    localStorage.setItem(this.STORAGE_KEY, theme);
  }

  toggleTheme(): void {
    const newTheme = this.currentTheme() === 'light' ? 'dark' : 'light';
    this.setTheme(newTheme);
  }

  private applyThemeToDOM(theme: ThemeOption): void {
    const body = document.body;
    body.classList.remove('theme-light', 'theme-dark');
    body.classList.add(`theme-${theme}`);
  }
}
```

### Step 2: Define Theme Colors in SCSS

**`src/assets/styles/variables/colors-light.scss`**

```scss
body.theme-light {
  // Primary colors
  --ion-color-primary: #2F71A2;
  --ion-color-primary-rgb: 47, 113, 162;
  --ion-color-primary-contrast: #FFFFFF;
  --ion-color-primary-shade: #1D4766;
  --ion-color-primary-tint: #5097CC;

  // Secondary colors
  --ion-color-secondary: #FF9933;
  --ion-color-secondary-rgb: 255, 153, 51;
  --ion-color-secondary-contrast: #FFFFFF;
  --ion-color-secondary-shade: #E57200;
  --ion-color-secondary-tint: #FFBF7F;

  // Status colors
  --ion-color-success: #10DC60;
  --ion-color-warning: #FFCE00;
  --ion-color-danger: #F04141;

  // Background & text
  --ion-background-color: #FFFFFF;
  --ion-background-color-rgb: 255, 255, 255;
  --ion-text-color: #000000;
  --ion-text-color-rgb: 0, 0, 0;
  --ion-border-color: #CCCCCC;

  // Neutrals
  --ion-color-light: #F4F5F8;
  --ion-color-light-rgb: 244, 245, 248;
  --ion-color-light-contrast: #000000;
  --ion-color-light-shade: #D8D8DC;
  --ion-color-light-tint: #F5F6FA;

  --ion-color-medium: #989AA2;
  --ion-color-medium-rgb: 152, 154, 162;
  --ion-color-medium-contrast: #FFFFFF;
  --ion-color-medium-shade: #86888F;
  --ion-color-medium-tint: #A2A4AB;

  --ion-color-dark: #222428;
  --ion-color-dark-rgb: 34, 36, 40;
  --ion-color-dark-contrast: #FFFFFF;
  --ion-color-dark-shade: #1E2023;
  --ion-color-dark-tint: #383A3E;
}
```

**`src/assets/styles/variables/colors-dark.scss`**

```scss
body.theme-dark {
  // Primary colors (Orange for dark)
  --ion-color-primary: #FF9933;
  --ion-color-primary-rgb: 255, 153, 51;
  --ion-color-primary-contrast: #000000;
  --ion-color-primary-shade: #E57200;
  --ion-color-primary-tint: #FFAD5B;

  // Secondary colors (Blue for dark)
  --ion-color-secondary: #2F71A2;
  --ion-color-secondary-rgb: 47, 113, 162;
  --ion-color-secondary-contrast: #FFFFFF;
  --ion-color-secondary-shade: #1D4766;
  --ion-color-secondary-tint: #5097CC;

  // Status colors (adjusted for dark)
  --ion-color-success: #2FDF75;
  --ion-color-warning: #FFD534;
  --ion-color-danger: #FF6B6B;

  // Background & text
  --ion-background-color: #1A1A1A;
  --ion-background-color-rgb: 26, 26, 26;
  --ion-text-color: #E8E8E8;
  --ion-text-color-rgb: 232, 232, 232;
  --ion-border-color: #444444;

  // Neutrals
  --ion-color-light: #3A3A3A;
  --ion-color-light-rgb: 58, 58, 58;
  --ion-color-light-contrast: #FFFFFF;
  --ion-color-light-shade: #333333;
  --ion-color-light-tint: #4D4D4D;

  --ion-color-medium: #888888;
  --ion-color-medium-rgb: 136, 136, 136;
  --ion-color-medium-contrast: #FFFFFF;
  --ion-color-medium-shade: #7A7A7A;
  --ion-color-medium-tint: #959595;

  --ion-color-dark: #F4F5F8;
  --ion-color-dark-rgb: 244, 245, 248;
  --ion-color-dark-contrast: #000000;
  --ion-color-dark-shade: #D8D8DC;
  --ion-color-dark-tint: #F5F6FA;
}
```

### Step 3: Bundle Themes

**`src/assets/styles/theme.scss`**

```scss
// Import theme color definitions
@import 'variables/colors-light.scss';
@import 'variables/colors-dark.scss';

// Default to light theme if no class is set
body {
  @extend body.theme-light;
}
```

### Step 4: Update Global Styles

**`src/global.scss` or `src/styles.scss`**

```scss
// ... existing Ionic imports ...

@import 'assets/styles/theme.scss';
```

### Step 5: Initialize Theme in App

**`src/app/app.component.ts`**

```typescript
import { Component, OnInit } from '@angular/core';
import { ThemeService } from './core/theme/theme.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    // Theme is initialized in ThemeService constructor
    // No additional setup needed here
  }

  toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  get currentTheme() {
    return this.themeService.currentTheme;
  }
}
```

## Usage in Components

No per-component customization needed! All Ionic components automatically use the CSS variables.

**Example button in any component:**

```html
<!-- Automatically uses --ion-color-primary from current theme -->
<ion-button expand="block">Save</ion-button>

<!-- Secondary action uses --ion-color-secondary -->
<ion-button color="secondary" expand="block">Cancel</ion-button>

<!-- Status colors work out-of-the-box -->
<ion-button color="success">Confirm</ion-button>
<ion-button color="danger">Delete</ion-button>
```

## Theme Toggle UI (Example)

**`src/app/core/layout/header.component.html`**

```html
<ion-toolbar>
  <ion-title>WebSmartStorage</ion-title>
  <ion-buttons slot="end">
    <ion-button (click)="toggleTheme()">
      <ion-icon [name]="currentTheme() === 'light' ? 'moon' : 'sunny'"></ion-icon>
    </ion-button>
  </ion-buttons>
</ion-toolbar>
```

## Result

- ✅ **No per-component customization** — all components inherit theme colors via CSS variables
- ✅ **One-click theme toggle** — instant switch across entire app
- ✅ **Persistence** — theme preference saved to LocalStorage
- ✅ **Maintenance-free** — future color adjustments affect all components at once
- ✅ **Brand-aligned** — blue/orange palette from logo applied globally
