#!/usr/bin/env node

const { writeFile, readFile, readdir } = require('fs');
const { promisify } = require('util');
const path = require('path');

const folder = 'node_modules/react-native/Libraries/Renderer/oss/';

const pattern = new RegExp(
  'invariant\\([\\s\\S]{0,20}' +
  '(hostContext|type)\\.isInAParentText,[\\s\\S]{0,20}' +
  '"Text strings must be rendered within a <Text> component\\."[\\s\\S]{0,20}' +
  '\\)[;,]'
);

const patchFile = async (file) => {
  const content = (await promisify(readFile)(file)).toString();
  const patched = content.replace(pattern, '');

  await promisify(writeFile)(file, patched);
};

const patchAll = async () => {
  const files = await promisify(readdir)(folder);

  await Promise.all(files.map(file => path.join(folder, file)).map(patchFile));
};

patchAll();
