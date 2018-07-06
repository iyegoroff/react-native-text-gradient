#!/usr/bin/env node

const fs = require('fs');
const { promisify } = require('util');
const glob = require('glob');

const patterns = [
  new RegExp(
    'type === "AndroidTextInput" \\\|\\| // Android[\\s\\S]+' +
    'type === "RCTMultilineTextInputView" \\|\\| // iOS[\\s\\S]+' +
    'type === "RCTSinglelineTextInputView" \\|\\| // iOS[\\s\\S]+' +
    'type === "RCTText" \\|\\|[\\s\\S]+' +
    'type === "RCTVirtualText"'
  ),

  new RegExp(
    '"AndroidTextInput" === props \\|\\|[\\s\\S]+' +
    '"RCTMultilineTextInputView" === props \\|\\|[\\s\\S]+' +
    '"RCTSinglelineTextInputView" === props \\|\\|[\\s\\S]+' +
    '"RCTText" === props \\|\\|[\\s\\S]+' +
    '"RCTVirtualText" === props'
  )
];

const patches = [
  'type.includes("Text")',

  'props.includes("Text")'
]

const patchFile = async (file) => {
  const content = (await promisify(fs.readFile)(file)).toString();
  const patched = content
    .replace(patterns[0], patches[0])
    .replace(patterns[1], patches[1])

  await promisify(fs.writeFile)(file, patched);
};

const patchAll = async () => {
  const files = await promisify(glob)('node_modules/react-native/Libraries/Renderer/oss/*');

  await Promise.all(files.map(patchFile));
};

patchAll();
