# Contributing

By contributing to Wilder Wild, you agree with the [Developer Certificate of Origin (DCO)][DCO].

## Conventions

### Naming

`UpperCamelCase` should be used for class names.
`lowerCamelCase` should be used for method names, variable names, and variables that aren't both static and final.
`UPPER_SNAKE_CASE` should be used for names of fields that are both static and final, although excluding atomics like `AtomicBoolean`, `AtomicInteger`, and `AtomicReference`.

Class names + non-boolean fields and variables should be noun phrases like `LivingEntity` and `level`.
Method names should generally be verb phrases like `tick` or `getValue`, but there can be some exceptions such as
builder methods, `of`, `withPos`, `toX`, etc.
Boolean fields and variables should always be adjective phrases or present tense verb phrases like `waterlogged` and `hasFluid`, while avoiding the `is` and `has` prefixes if possible (`colored` instead of `isColored` or `hasColor`).

To keep code easy to read, try to keep names in the natural language order. For example, a class representing an
entity model should be named `EntityModel` rather than `ModelEntity`. Although prefix naming may be
helpful for grouping classes together in an IDE's tree view, reading and writing code is done much more often
than browsing files.

### Spelling

Use American English to keep consistency across Wilder Wild.

If there is more than one acceptable spelling of the same word, first check if one word is already
being used in Wilder Wild, FrozenLib, or in Mojang's Mappings, and if not, use the spelling that is most commonly used.

### Abbreviations

Avoid using abbreviations unless it's a common one everyone knows about and names in Mojang's Mappings
uses the abbreviated form of the word. Full names are easier to read quickly and they often don't take
much more time to type now, mainly thanks to IDE autocompletion. Common abbreviations that should be used are:

- "pos" for "position"
- "prev" for "previous"
- "min"/"max" for "minimum"/"maximum"
- "id" for "identifier"
- "init" for "initialize"
- "Any abbreviations used by Java or other libraries ("json", "html", "yml", etc.)

Abbreviations you shouldn't use are:

- "loc" for "location"

Acronyms should be treated as single words rather than having capitalization on every letter. This improves
readability and is consistent with naming in Mojang's Mappings.

Avoid the use of abbreviations in javadocs, except if they describe the name of a format or library.

### Mixins

`@Redirect` should never be used in a case where a `WrapOperation` could be used.

`@ModifyConstant` should never be used in a case where a `ModifyExpressionValue` could be used.

The `$` character can be used in mixins to mark a semantic separation in the name.
It allows to separate the actual name of the variable and the namespace, `wilderWild`.

Fields marked as `@Unique` must be prefixed with `wilderWild$` or `WILDERWILD$` if the field is static and final.

Methods marked with `@Unique` must be prefixed with `wilderWild$`.

Injector or modifier methods do not need to be prefixed. Fabric takes care of that.

## Licensing & DCO

Wilder Wild is licensed under [GPL 3.0][LICENSE], and it has a [Developer Certificate of Origin (DCO)][DCO], which you are required to agree with to contribute.
Specifying the commit author may be sufficient, but a [sign-off can be also added](https://git-scm.com/docs/git-commit#Documentation/git-commit.txt--s).
Legal names are not required.

---

**Note: This document is currently incomplete.**

[LICENSE]: ./LICENSE "Wilder Wild license file"

[DCO]: ./DEVELOPER_CERTIFICATE_OF_ORIGIN.md "Developer Certificate of Origin file"
