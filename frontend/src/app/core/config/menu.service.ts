import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { signal } from '@angular/core';
import { MenuConfig, MenuGroup } from './menu.model';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private readonly MENU_CONFIG_URL = 'assets/config/menu.json';

  menuGroups = signal<MenuGroup[]>([]);
  isLoading = signal(false);
  error = signal<string | null>(null);

  constructor(private http: HttpClient) {
    this.loadMenuConfig();
  }

  private loadMenuConfig(): void {
    this.isLoading.set(true);
    this.error.set(null);

    this.http.get<MenuConfig>(this.MENU_CONFIG_URL).subscribe({
      next: (config) => {
        this.menuGroups.set(config.menuGroups);
        this.isLoading.set(false);
      },
      error: (err) => {
        console.error('Failed to load menu config:', err);
        this.error.set('Failed to load menu configuration');
        this.isLoading.set(false);
      }
    });
  }

  /**
   * Find a menu item by route
   */
  findItemByRoute(route: string) {
    for (const group of this.menuGroups()) {
      const item = group.items.find(item => item.route === route);
      if (item) return item;
    }
    return null;
  }

  /**
   * Find a menu group by id
   */
  findGroupById(groupId: string) {
    return this.menuGroups().find(group => group.id === groupId);
  }
}
