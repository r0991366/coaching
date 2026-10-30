const fs = require('fs');
const path = require('path');

const distPath = path.join(__dirname, 'dist');

function fixFileExtensions(directory) {
  fs.readdirSync(directory).forEach((file) => {
    const fullPath = path.join(directory, file);

    if (fs.lstatSync(fullPath).isDirectory()) {
      fixFileExtensions(fullPath);
    } else if (fullPath.endsWith('.js')) {
      let content = fs.readFileSync(fullPath, 'utf8');

      content = content.replace(
        /(import .* from\s+['"])(\.\.?\/[^'"]+)(['"])/g,
        '$1$2.js$3'
      );

      fs.writeFileSync(fullPath, content, 'utf8');
    }
  });
}

console.log('Starting post-build script to fix import extensions...');
fixFileExtensions(distPath);
console.log('Successfully fixed import extensions for the browser.');
