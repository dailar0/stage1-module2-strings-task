package com.epam.mjc;

import java.util.List;
import java.util.stream.Collectors;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        String signatureWOArgs = signatureString.split("\\(.*\\)")[0];
        String args = signatureString.substring(signatureWOArgs.length());

        String[] signature = signatureWOArgs.split("\\s");
        boolean isAccessModified = signature.length > 2;

        MethodSignature methodSignature;

        StringSplitter stringSplitter = new StringSplitter();
        List<String> arguments = stringSplitter.splitByDelimiters(args, List.of(",", "(", ")"));
        List<MethodSignature.Argument> argumentList = arguments
                .stream()
                .map(s -> {
                    String[] pair = s.trim().split(" ");
                    return new MethodSignature.Argument(pair[0], pair[1]);
                })
                .collect(Collectors.toList());

        if (isAccessModified) {
            String accessModifier = signature[0];
            String returnType = signature[1];
            String methodName = signature[2];
            methodSignature = new MethodSignature(methodName, argumentList);
            methodSignature.setReturnType(returnType);
            methodSignature.setAccessModifier(accessModifier);
        } else {
            String returnType = signature[0];
            String methodName = signature[1];
            methodSignature = new MethodSignature(methodName, argumentList);
            methodSignature.setReturnType(returnType);
        }

        return methodSignature;
    }
}
