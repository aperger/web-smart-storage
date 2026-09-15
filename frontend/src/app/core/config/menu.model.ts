/**
 * Menu configuration models for two-level JSON-driven menu.
 * Level 1: MenuGroup (accordion)
 * Level 2: MenuItem (navigable items)
 */

export interface MenuItem {
  id: string;
  label: string;
  route: string;
  icon: string;
}

export interface MenuGroup {
  id: string;
  label: string;
  icon: string;
  items: MenuItem[];
}

export interface MenuConfig {
  menuGroups: MenuGroup[];
}
