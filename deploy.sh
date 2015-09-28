#! /bin/bash

which gitbook >& /dev/null || (echo "[ERR]Please install gitbook." && false) || exit 1

(rm -rf _book && gitbook build) || (echo "[ERR]Build book failed." && false) || exit 1

(cd _book && git init && git remote add github git@github.com:FishballInteractive/5MiaoSDK-Android.git && \
  git add -A . && git commit -m 'Newest document' && git push -f github HEAD:gh-pages) || \
  (echo "[ERR]Upload document to github failed." && false) || exit 1
