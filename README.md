# MyBus Android
=====================================================================================================================
MyBus Android is an application for http://www.mybus.com.ar

## git Workflow Guidelines

These document is intended to be used as guidelines and not as a tutorial of git.

* In order to have a robust schema of redundant code but with a clear difference between the version it has been proposed to have several branches:

```
| . production
|/
. master (development merged)
|\   |
| |  . sprint-W
| | /
| .
| | . release-sprint-X_[month_Y]_[day_Z]
| |/
| . development (sprint-X merged)
| | \
| |  . sprint-X (MYB-2_[name_handler] merged)
| |  |\
| |  | . MYB-2_[name_handler]
| |  |/
| |  . sprint-X (MYB-1_[name_handler] merged)
| |  |\ 
| |  | . MYB-1_[name_handler]
| |  |/
| |  . sprint-X
| | /
| . development
|/ 
. master
```

### Branches Rebasing Process

If you need to build a new feature, it should come out from develop. After you are done working on that feature, and that code has been reviewed you should:

```git checkout sprint-X```

```git pull origin sprint-X```

```git checkout MYB-FeatureBranch```

```git rebase -i sprint-X```

You will see a ‘vi’ console with your commits, you should squash them into one commit.

If ```git rebase -i sprint-X``` is successful you should:

```git checkout sprint-X```

```git merge MYB-FeatureBranch```

### Branches Conflict Solving Process

If you happen to find conflicts when merging or rebasing your branch when doing the squash of you commits you should resolved those issues on Xcode regarding which parts of the code will stay in the final commit.
Then you should:

```git add ./Path/Of/The/Conflicted/File```

```git commit -m “MYB-XYZ - Resolved merge conflicts from MYB-FeatureBranch into sprint-X```

Then you should finish your rebasing by

```git rebase --continue```. If your rebasing gets too complicated or difficult to resolve ask for assistance.

#### Pushing Changes (Pure Push Scheme Procedure)

If you were able to rebase successfully, should be able to perform the following command without any problem:

```git push origin sprint-X```

#### Pushing Changes (Pull Request Scheme Procedure)

If you were able to rebase successfully, should be able to create an updated pull request to merge against the current sprint branch.

NOTE: If you need to add or remove assets, classes or any other kind of resource that will inevitably modify the .xcodeproj file, please inform via Skype/Slack to the MyBus Android Team chat group so everybody can pull the project changes. Then you can start working on your code-related task. Clearly, you do not need to create a pull request in order to have this sorted out.
