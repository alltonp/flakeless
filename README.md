<a href="https://travis-ci.org/alltonp/flakeless" target="_blank"><img src="https://travis-ci.org/alltonp/flakeless.png?branch=master"></a>


# Flakeless - light touch, flake free browser testing in scala

### What is it?
- A lightweight library that wraps WebDriver for easy integration into both new and existing projects

### Why would I use it?
- You want to write your tests in blissful ignorance of async/ajax browser updates
- You never want to write another sleep, explicit, implicit or fluent wait
- You value zero tolerance to flaky tests
- You value strong assertions (i.e. 'assert Element Not Found' is the most useless assertion one could possibly write - so Flakeless does not support it)
- You want zero-effort test reporting

### How?
- No big bang changes, Flakeless lets you gradually migrate your tests one interaction at a time
- Replace each browser action/assertion with one of Flakeless' simple [Tell Dont Ask](https://martinfowler.com/bliki/TellDontAsk.html) style [primitives](src/main/scala/im/mange/flakeless/)
- Unable to find a primitive for X - raise an Issue

### Sounds good, how do I start eradicating my existing flaky tests?
- Read the [Migration Guide](src/example/scala/im/mange/flakeless/examples/MigrationGuide.scala)

### Sounds good, how do I using flakeless on a new project?
- Read the New Project Guide (coming soon)

### I'm using it, how do I better understand my test suite?
- Read the Reports Guide (coming soon)


### Installing

Add the following lines to your build.sbt (click on the 'build passing' link above to get the version number)

    resolvers += "Sonatype Repo" at "http://oss.sonatype.org/content/groups/public/"

    libraryDependencies += "im.mange" %% "flakeless" % "version"


-----

Copyright © 2016-2017 Spabloshi Ltd
