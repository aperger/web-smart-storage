module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  testPathIgnorePatterns: [
    '<rootDir>/node_modules/',
    '<rootDir>/dist/',
    '<rootDir>/e2e/'
  ],
  extensionsToTreatAsEsm: ['.ts'],
  transform: {
    '^.+\\.(ts|mjs|js|html)$': [
      'jest-preset-angular',
      {
        tsconfig: '<rootDir>/tsconfig.spec.json',
        stringifyContentPathRegex: '\\.(html|svg)$',
        useESM: true
      }
    ]
  },
  transformIgnorePatterns: [
    'node_modules/(?!(@angular|@ionic|ionicons)/)'
  ],
  moduleNameMapper: {
    '^ionicons(.*)$': '<rootDir>/node_modules/ionicons$1',
    '^ionicons/components/ion-icon': '<rootDir>/node_modules/ionicons/dist/esm/components/icon/icon'
  },
  moduleFileExtensions: ['ts', 'html', 'js', 'json', 'mjs'],
  snapshotSerializers: [
    'jest-preset-angular/build/serializers/no-ng-attributes',
    'jest-preset-angular/build/serializers/ng-snapshot',
    'jest-preset-angular/build/serializers/html-comment'
  ],
  collectCoverageFrom: [
    'src/**/*.ts',
    '!src/**/*.spec.ts',
    '!src/main.ts',
    '!src/polyfills.ts'
  ],
  coverageDirectory: '<rootDir>/coverage',
  coverageReporters: ['html', 'text', 'lcov', 'json'],
  testEnvironment: 'jsdom'
};


