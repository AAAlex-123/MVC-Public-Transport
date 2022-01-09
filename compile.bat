DIR /A-D /B /S src\*.java > .files_to_compile

javac -d bin --class-path "lib/*" -g:none --release 8 @.files_to_compile

COPY /V src\localisation\*.properties /A bin\localisation /A

DEL .files_to_compile
