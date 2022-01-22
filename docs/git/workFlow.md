# 工作流



## 一.中心式协同工作流

简述 : 所有成员都在同一个分支上进行开发,上线之前进行合并

问题 : 代码干扰严重。尤其是，我们想安安静静地开发一个功能时，我们想把各个功能的代码变动隔离开来，同时各个功能又会有多个开发人员在开发。

开发过程 : 
- 从服务器上做git pull origin master把代码同步下来。
- 改完后，git commit到本地仓库中。
- 然后git push origin master到远程仓库中.
- 出现冲突 : 从服务器上做git pull origin master把代码同步下来。
- 改完后，git commit到本地仓库中。然后git push origin master到远程仓库中

## 二. 功能分支协同工作流

简述 : 以服务器为中心的开发，它只不过是用分支来完成代码改动的隔离。

- 首先使用 git checkout -b new-feature 创建 “new-feature”分支。
- 然后共同开发这个功能的程序员就在这个分支上工作，进行 add、commit 等操作。
- 然后通过 git push -u origin new-feature 把分支代码 push 到服务器上。
- 其他程序员可以通过git pull --rebase来拿到最新的这个分支的代码。
- 最后通过 Pull Request 的方式做完 Code Review 后合并到 Master 分支上

## 三. GitFlow 协同工作流

整个代码库中一共有五种分支。

- Master 分支。也就是主干分支，用作发布环境，上面的每一次提交都是可以发布的。
- Feature 分支。也就是功能分支，用于开发功能，其对应的是开发环境。
- Developer 分支。是开发分支，一旦功能开发完成，就向 Developer 分支合并，合并完成后，删除功能分支。这个分支对应的是集成测试环境。
- Release 分支。当 Developer 分支测试达到可以发布状态时，开出一个 Release 分支来，然后做发布前的准备工作。这个分支对应的是预发环境。
- Hotfix 分支。是用于处理生产线上代码的 Bug-fix，每个线上代码的 Bug-fix 都需要开一个 Hotfix 分支，完成后，向 Developer 分支和 Master 分支上合并。合并完成后，删除 Hotfix 分支。

>之所以需要这个 Release 分支，是我们的开发可以继续向前，不会因为要发布而被 block 住而不能提交。一旦 Release 分支上的代码达到可以上线的状态，那么需要把 Release 分支向 Master 分支和 Developer 分支同时合并，以保证代码的一致性。然后再把 Release 分支删除掉。


问题 : 
- 因为分支太多，所以会出现 git log 混乱的局面
- 在开发得足够快的时候，会觉得同时维护 Master 和 Developer 两个分支是一件很无聊的事


## 四. GitHub Flow

- 每个开发人员都把“官方库”的代码 fork 到自己的代码仓库中。
- 然后，开发人员在自己的代码仓库中做开发，想干啥干啥。
- 因此，开发人员的代码库中，需要配两个远程仓库，一个是自己的库，一个是官方库（用户的库用于提交代码改动，官方库用于同步代码）。
- 然后在本地建“功能分支”，在这个分支上做代码开发。
- 这个功能分支被 push 到开发人员自己的代码仓库中。
- 然后，向“官方库”发起 pull request，并做 Code Review。
- 一旦通过，就向官方库进行合并。













