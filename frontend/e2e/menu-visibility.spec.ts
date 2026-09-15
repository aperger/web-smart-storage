import { test, expect } from '@playwright/test';

test('desktop shows left menu', async ({ page }) => {
  await page.setViewportSize({ width: 1440, height: 900 });
  await page.goto('/');

  const menu = page.locator('ion-menu');
  await expect(menu).toBeVisible();
  await expect(page.getByText('Base Data')).toBeVisible();
});
