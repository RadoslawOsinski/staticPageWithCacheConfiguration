import Configuration from '/configuration.js';

const config = new Configuration();
console.log('Some configuration applicationUrl: ' + config.getConfiguration().applicationUrl);
console.log('Some configuration environment: ' + config.getConfiguration().environment);
