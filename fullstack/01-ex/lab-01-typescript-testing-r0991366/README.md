# Getting Started with the back-end

## Prerequisites

### Node.js and npm

To run TypeScript code and manage project dependencies, you need Node.js.
Node.js is a JavaScript runtime that allows us to execute JavaScript code outside of a browser.
The Node Package Manager (npm) is included with every installation of Node.js and is used to install libraries and run scripts.

You can download Node.js from <https://nodejs.org/en/download/>.
Make sure to download the LTS version.

Verification: To confirm that Node.js and npm are installed correctly, open a new terminal (you can use the integrated terminal in VSCode via Terminal > New Terminal) and run the following commands:

```Bash
node -v
npm -v
```

## Install dependencies

Install all the required libraries for the project, such as Jest and TypeScript. These are defined in the package.json file.

Run the following command in the terminal inside your project directory:

```console
> npm install
```

This command reads the package.json file and downloads all necessary packages into a node_modules folder.
