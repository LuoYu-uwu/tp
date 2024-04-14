# Willson Han Zhekai - Project Portfolio Page

## Overview
Grocery in Time (GiT) is a **grocery tracker app**, optimised for use via a Command Line Interface (CLI).
It allows users to track and manage their groceries around their home easily.



### Summary of Contributions

#### Code contributed: [RepoSense link of contributions](https://nus-cs2113-ay2324s2.github.io/tp-dashboard/?search=wallywallywally&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-02-23&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code&tabOpen=true&tabType=authorship&tabAuthor=wallywallywally&tabRepo=AY2324S2-CS2113-T12-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)


#### Enhancements
1. Ability to **edit** the amount of a grocery
   - `amt GROCERY a/AMOUNT`: Set amount
   - `use GROCERY a/AMOUNT`: Decrease amount after using
2. Functionalities related to **storage locations**
   - `loc LOCATION`: Add location to be tracked
   - `delloc LOCATION`: Remove tracked location
   - `store GROCERY l/LOCATION`: Store grocery in a given location
   - `listloc [LOCATION]`
     - Without `LOCATION`: View all tracked locations
     - With `LOCATION` View groceries stored in given `LOCATION`
   - Loading storage location data
3. Ability to **find** groceries by name: 
   - `find KEYWORD`
4. Improved defensiveness
   - Created custom exceptions with helpful error messages
   - Tested and fixed various bugs

    
#### Contributions to documentation
1. #### User Guide
   - Documentation for various enhancements
     - `add`, `addmulti`, `cat`, `amt`, `use`, `store`, `find`, `del`, `listloc`, `loc`, `delloc`
   - General information
     - Introduction, Quick Start, Command Summary
   - Enhancements to overall formatting and readability
     - Table of Contents
2. #### Contributions to the Developer Guide
   - Design and implementation details
     - For features `amt` and `use`
     - Sequence diagram for `use`
     - Class diagram for `GroceryList`
   - Product scope
     - Target user profile, Value proposition, User stories


#### Contributions to Team-Based Tasks
- Set up GitHub organisation and repository
- General code enhancements regarding code readability, with a focus on exceptions
- Maintained issue tracker and milestones
  - Created and delegated issues
- Released v2.0


#### Review/mentoring contributions
As shown in the following PRs: 
[#19](https://github.com/AY2324S2-CS2113-T12-2/tp/pull/19), 
[#52](https://github.com/AY2324S2-CS2113-T12-2/tp/pull/52),
[#89](https://github.com/AY2324S2-CS2113-T12-2/tp/pull/89),
[#151](https://github.com/AY2324S2-CS2113-T12-2/tp/pull/151)


#### Contributions beyond the project team
[Reviewed another team's Developer Guide](https://github.com/nus-cs2113-AY2324S2/tp/pull/41)


&nbsp;
## Examples of documentation contributions
[//]: # (to update)
## 1. Extracts from the User Guide

### Listing storage locations and their groceries: `listloc`
View all storage locations being tracked, or the groceries stored in a given location

Format: `listloc [LOCATION]`

* `LOCATION` is an optional parameter.
    * Without `LOCATION`, all storage locations will be displayed.
    * With `LOCATION`, all groceries in the given `LOCATION` will be displayed.
* More information on storage locations can be found [here](#manage-storage-locations).

Example of usage:

* `listloc`: All storage locations are displayed.

```
>> listloc

Here's all the locations you are tracking:
- spice rack
- freezer
- cubby
```

* `listloc cubby`: All groceries in `cubby` are displayed.

```
>> listloc cubby

Viewing location: cubby
Here are your groceries!
- cheese (dairy), amount: 50, location: cubby
- pasta (carbs), cost: $2.95, location: cubby
```

## 2. Extracts from the Developer Guide
![EditAmount example 1](wallyimgs/editAmt_1.png)
![EditAmount example 2](wallyimgs/editAmt_2.png)