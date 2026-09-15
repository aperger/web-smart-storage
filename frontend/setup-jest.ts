import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';

// Initialize Angular testing environment
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);

// Configure DOM polyfills for Ionic components
Object.defineProperty(window, 'CSS', {value: null});

Object.defineProperty(window, 'getComputedStyle', {
  value: () => {
    return {
      display: 'none',
      appearance: ['-webkit-appearance']
    };
  }
});

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

Object.defineProperty(document, 'doctype', {
  value: '<!DOCTYPE html>'
});

Object.defineProperty(HTMLElement.prototype, 'scrollTo', {
  configurable: true,
  value: () => {}
});

const originalWarn = console.warn;
const originalLog = console.log;

console.warn = (...args: unknown[]) => {
  const message = String(args[0] ?? '');
  if (message.includes('[Ionicons Warning]') || message.includes('[Ionic Warning]')) {
    return;
  }
  originalWarn(...args);
};

console.log = (...args: unknown[]) => {
  const message = String(args[0] ?? '');
  if (message === 'e' || message.includes('Invalid base URL')) {
    return;
  }
  originalLog(...args);
};
