miniauth
========

_MiniAuth OAuth signature library in Java_


What is it?
---

`MiniAuth` is a stand-alone Java library for generating / verifying OAuth signatures (for OAuth 1.0a).
   * It has no dependency on third party libraries other than JDK (JDK7, see the note below).
   * It is light weight. It does not include full features of OAuth (like "OAuth dance", etc.). 
   * The core library (core and oauth modules) does not even use _javax.servlet_ packages.
   * The main purpose of the library is
       1. To make request signing less painful on the client side. (Acquiring a user's access token is outside the scope of MiniAuth, at least, at this point.)
       1. To facilitate easy verification of OAuth signed request on the server side (without having to use a full OAuth server/provider library).


How to use it?
---

If you use Maven, you can build and (locally) install the `MiniAuth` library using standard Maven goals. 
(If you don't use Maven, then there is an ant script under _nomaven/scripts_.)
You can specify the dependency as follows:

  	<dependency>
      <groupId>org.aerysoft.miniauth</groupId>
      <artifactId>miniauth-oauth</artifactId>
      <version>0.9.1</version>
  	</dependency>



High Level API Design
---

We have three "levels" of APIs, from low to high:
   1. OAuth (v1) signature generation / verification utils, including crypto support, etc.
   1. Generic auth support layer, including generating HTTP "Authorization: " headers, etc.
   1. Integration of auth into common HTTP client or server frameworks.

Currently, only the lowest level (OAuth signature-related API) has been implemented.
The two higher level APIs will be done in the near future.



Modules
---

Initially, `MiniAuth` started as a single module (for OAuth v1.0a). 
We are currently in the process of refactoring the code to extend the scope of the library to include different authentication methods.
Please refer to the POM files for module dependencies.


#### [1] Core Module ####
This module is used for shared/common classes among different authentication methods.
 

#### [2] OAuth Module ####
This was the original main part of the library.
(The `MiniAuth` project started because I needed a simple library to verify OAuth signatures in my server applications.)
This module includes the OAuth signature generation/verification methods, among other things.
If you are looking for OAuth signature library, then this is the module you need to use.
It has dependency on the core module.


#### [3] OAuth2 Module ####
Place holder. OAuth2 is order of magnitude simpler than OAuth. 
(For one thing, it does not require signing requests.)
This module will remain mostly empty unless we decide to include full OAuth client implementation in `MiniAuth`.


#### [4] Web Module ####
Place holder. The name is a bit misleading, but this module will be used to include "wrapper" classes
so that `MiniAuth` can be easily used in the servlet-based Web framework/servers (for the Auth providers)
or in the client applications that use URLConnection or HttpClient, etc. (for the Auth consumers).



API
---

Please refer to [the Project wiki pages](https://github.com/harrywye/miniauth/wiki/_pages) or [the online API Docs](http://www.miniauth.org/repo/apidocs/).



Notes on the Use of JDK7
---

`MiniAuth` uses some of the JDK7 features such as "Switch with string values", "Multi-catch block", "Diamond operator", and "Try with resources", etc.
If you need to use an earlier version of JDK, then you may have to check out the source code and modify some parts.
You can easily change the JDK version in the main POM file or nomaven ant script. (Look for "1.7" if you are not familiar with Maven or Ant.)


