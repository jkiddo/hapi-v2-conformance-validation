# hapi-v2-conformance-validation
An extension to James Agnew's HL7v2 framework that incorporates the use of IHE validation components.

While many of us in the healthcare domain are eager to get some more action and tracktion on exchanging data using FHIR, we will probably still be using HL7 v2 for years to come (which means more arcane interfaces and so on). To aid the process of using HL7v 2.x, I've made a small extension to the HAPI framework making it easier to incorporate the use of conformance profiling as described in chapter 2.12 in HL7 v2.5 (section 2.B in version 2.8) and also incorporating it's use as IHE does it - an organization making heavy use of conformance profiles (I still don't understand people who uses v2.x without conformance profiles - hint, they are actually backwards compatible).

Schemas for the conformance profiles can be found @ https://github.com/oehf/ipf-gazelle/tree/master/commons/core/src/main/resources
The validator code is copied from https://scm.gforge.inria.fr/anonscm/svn/gazelle/Maven/GazelleHL7v2Validator/trunk/GazelleHL7v2Validator-ejb

## How
The services exposes a REST interface (documented pr. WADL - example available @ http://hl7-jkiddo.rhcloud.com/validation/application.wadl) that can be used to test conformance betweeen a message and a conformance profile. If deployed locally, a Hats'n'pipes service is also available at port 2575.

All the listed paths below are relative to http://hl7-jkiddo.rhcloud.com/validation (which happens to be the place where an instance is also deployed)

##### GET /conformance 
Gets all currently loaded profiles

##### GET /conformance/{someId}
Gets a specific profile with the current ID.

##### POST /conformance
Adds the conformance profile in the body of the request to the set of available conformance profiles with the ID specified in the profile.

##### POST /conformance/{someId}
Adds the conformance profile in the body of the request to the set of available conformance profiles with the specified ID.

##### POST /conformance/test
Tests the HL7v2 hats'n'pipes message located in the body of the request against the profile specified with the ID in MSH-21 in the message.


Profiles from IHE can be found at svn://scm.gforge.inria.fr/svnroot/gazelle/Data/trunk or https://gforge.inria.fr/scm/viewvc.php/gazelle/Data/trunk/
