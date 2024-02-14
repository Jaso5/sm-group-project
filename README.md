# SM-GROUP-PROJECT

## Shortcuts (IntelliJ Idea)

Build: `Dockerfile`

## Recommended Tools
 - IDE: IntelliJ Idea
 - Git: <https://github.com/petervanderdoes/gitflow-avh/wiki>

## Getting Started
```shell
git clone https://github.com/Jaso5/sm-group-project.git
```
Use the `Dockerfile` to build + run the project, Idea should pick it up as a build config, alternatively right-click `Dockerfile` and hit build.

### Common Tasks

#### Creating a feature branch
```shell
git flow feature start MYFEATURE
git add .
git commit -m "Added files"
git flow feature finish MYFEATURE
git flow feature publish MYFEATURE  
```