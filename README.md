# just-gif-it
Spring boot example project

## Command Line Project Creation
`curl start.spring.io/starter.zip -o demo.zip --data @options.curl`
see the options file for dependencies and project name, artifact id etc.

## Upload Video With CURL
`curl -F file=@cats.mp4 -F start=0 -F end=0 -F speed=1 -F repeat=0 localhost:8080/upload`

## Properties
* `com.fererlab.create-result-dir #true/false`
* `com.fererlab.optimize #true/false`
