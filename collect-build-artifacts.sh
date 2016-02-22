#!/usr/bin/env bash
#
# Copyright © 2011 - 2013 Aaron Mahan
# Copyright © 2013 - 2016 Forerunner Games, LLC
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#

OUTPUT_DIR="build/collected"
ARTIFACTS=("build/libs" "build/reports" "build/outputs" "build/*.log")
ROOT_PROJECT=$(sed -n -e 's/^projectName=//p' gradle.properties)
SUBPROJECTS=($(sed -n -e 's/^include //p' settings.gradle | tr -d ",\"" | tr ' ' "\n"))

printf "\nCollecting build artifacts...\n\n"
printf "Working directory:\n\n"
printf "  %s\n\n" `pwd`
printf "Output directory:\n\n"
printf "  %s\n\n" `pwd`/${OUTPUT_DIR}
printf "Projects to collect from:\n\n"
for PROJECT in ${ROOT_PROJECT} ${SUBPROJECTS[@]}; do printf "  %s\n" ${PROJECT}; done
printf "\nArtifacts to collect from each project:\n\n"
for ARTIFACT in ${ARTIFACTS[@]}; do printf "  %s\n" ${ARTIFACT}; done
printf "\nCollecting:\n\n"

mkdir -p ${OUTPUT_DIR}

for PROJECT in ${ROOT_PROJECT} ${SUBPROJECTS[@]}; do
  for ARTIFACT in ${ARTIFACTS[@]}; do
    [[ -z $(find . -path "*${PROJECT}/${ARTIFACT}" -print -quit) ]] && printf "  %s NOT FOUND - SKIPPING\n" ${PROJECT}/${ARTIFACT}
    rsync -am --out-format="%f" ${PROJECT}/${ARTIFACT} ${OUTPUT_DIR}/${PROJECT}/ 2>/dev/null | awk '{ if ($1 > 0) { print "  " $1 } }'
  done
done
