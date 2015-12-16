# hapi-v2-conformance-validation
An extension to James Agnew's HL7v2 framework that incorporates the use of IHE validation components.

While many of us in the healthcare domain are eager to get some more action and tracktion on exchanging data using FHIR, we will probably still be using HL7 v2 for years to come (which means more arcane interfaces and so on). To aid the process of using HL7v 2.x, I've made a small extension to the HAPI framework making it easier to incorporate the use of conformance profiling as described in chaper 2.12 in HL7 v2.5 (section 2.B in version 2.8) and also incorporating it as IHE does it - an organisation making heavy use of conformance profiles (I still don't understand people who uses v2.x without conformance profiles - hint, they are actually backwards compatible).

Schema found @ http://gazelle.ihe.net/XSD/HL7/V2/HL7MessageProfileSchema.xsd