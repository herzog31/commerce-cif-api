/*******************************************************************************
 *
 *    Copyright 2018 Adobe. All rights reserved.
 *    This file is licensed to you under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License. You may obtain a copy
 *    of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under
 *    the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
 *    OF ANY KIND, either express or implied. See the License for the specific language
 *    governing permissions and limitations under the License.
 *
 ******************************************************************************/

'use strict';

const CI = require('./ci.js');
const ci = new CI();

ci.context();

ci.sh('echo $GPG_PRIVATE_KEY | base64 --decode | gpg --batch --import');
ci.sh('echo "pinentry-mode loopback" > /home/circleci/.gnupg/gpg.conf');

ci.stage('BUILD');
ci.sh('mvn -B clean install -DskipTests -s ci/settings.xml');

ci.stage('UNIT TESTS');
ci.sh('mvn -B verify -s ci/settings.xml');

ci.stage('BUILD DONE');