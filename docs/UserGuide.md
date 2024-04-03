# User Guide to ***Grocery in Time***

![Grocery in Time logo](images/GitStartup.png)


## Introduction

Grocery in Time (GiT) is a **grocery tracker app**, optimised for use via a Command Line Interface (CLI).
It allows users to track and manage their groceries around their home easily.

&nbsp;
## Quick Start
1. Ensure that you have Java 11 or above installed.
2. Down the latest version of `Grocery in Time` from [here](https://github.com/AY2324S2-CS2113-T12-2/tp/releases).
3. Open a command terminal, `cd` into the folder where the JAR file is
   and use `java -jar Git.jar` to run Grocery in Time.

&nbsp;
## Features 

> #### Notes about the command format
> * Words in `UPPERCASE` are parameters to be supplied by the user.
> <br> e.g. In `find KEYWORD`, `KEYWORD` is a parameter to be supplied: `find cheese`.
> 
> 
> * Features requiring the `GROCERY` input are case-insensitive. 
> <br> e.g. `amt GROCERY a/AMOUNT` will set the amount of `milk` or `MILK`.

&nbsp;
### Setting the amount of a grocery: `amt`
Sets the amount of a grocery.

Format: `amt GROCERY a/AMOUNT`

* `AMOUNT` must be a valid integer.

Example of usage:

`amt milk a/5`

&nbsp;
### Using a grocery: `use`
Reduce the amount of a grocery after using it.

Format: `use GROCERY a/AMOUNT`

* `AMOUNT` must be a valid integer.
* If `AMOUNT` is greater than what the `GROCERY` has in stock, its amount will be reduced to 0.
* If the amount of the `GROCERY` is already 0, GiT will let the user know and the amount stays at 0.

Example of usage:

`use meat a/4`

&nbsp;
### Finding groceries: `find`
Find groceries containing a given keyword.

Format: `find KEYWORD`

* The search is case-insensitive.
* If a phrase is passed, the entire phrase is searched for.

Example of usage:

`find cheese`
![Find example output](images/featureExampleOutputs/FindExOut.png)


&nbsp;
### Adding a storage location: `loc`
Add a storage location to be tracked.

Format: `loc LOCATION`

Example of usage:

`loc freezer`

&nbsp;
### Storing a grocery in a storage location: `store`
Store a grocery in a given storage location.

Format: `store GROCERY l/LOCATION`

* `LOCATION` is case-insensitive. e.g. `freezer` matches `FREEZER`.
* If `LOCATION` does not exist, GiT will create the storage location and store the `GROCERY` there automatically.

Example of usage:

`store paprika l/spice rack`

&nbsp;
### Viewing storage locations and their groceries: `listl`
View all storage locations being tracked, or the groceries stored in a given location

Format: `listl [LOCATION]`

* `LOCATION` is an optional parameter.
* Without `LOCATION`, all storage locations will be displayed.
* With `LOCATION`, all groceries in the given `LOCATION` will be displayed

Example of usage:

* `listl`

![Listl example output](images/featureExampleOutputs/ListlExOut.png)

* `listl cubby`

![Listl LOCATION example output](images/featureExampleOutputs/ListlLocExOut.png)

&nbsp;
### Removing a storage location: `delloc`
Remove a storage location from tracking.

Format: `delloc LOCATION`

Example of usage:

`delloc cabinet`

### List all groceries: `list`
Shows a list of all groceries you have.

Format: `list`

Example of usage:

`list`

### List all groceries by price: `listc`
Shows a list of all groceries you have, sorted by price.

Format: `listc`

Example of usage:

`listc`

### List all groceries by expiration date: `liste`
Shows a list of all groceries you have, sorted by expiration date.

Format: `liste`

Example of usage:

`liste`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary


| Command                                                        | Format and example         |
|----------------------------------------------------------------|----------------------------|
| Set grocery amount                                             | `amt GROCERY a/AMOUNT`     |
| Use grocery                                                    | `use GROCERY a/AMOUNT`     | 
| Find groceries                                                 | `find KEYWORD`             |
| Add storage location                                           | `loc LOCATION`             |
| Store grocery                                                  | `store GROCERY l/LOCATION` |
| View storage locations <br> View groceries in a given location | `listl [LOCATION]`         |
| Remove storage location                                        | `delloc LOCATION`          |