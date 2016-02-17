#!/usr/bin/env bash

ROOT_DIR="build/uploaded"

mkdir -p $ROOT_DIR

for MODULE in common net
do
  rsync --archive --prune-empty-dirs $MODULE/build/libs $ROOT_DIR/$MODULE/ >/dev/null 2>&1
  rsync --archive --prune-empty-dirs $MODULE/build/reports $ROOT_DIR/$MODULE/ >/dev/null 2>&1
done

rsync --archive build/reports $ROOT_DIR/root/ >/dev/null 2>&1
