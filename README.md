# Dapeng

[![Travis](https://img.shields.io/travis/atline/dapeng.svg)](https://travis-ci.org/atline/dapeng/)
[![GitHub release](https://img.shields.io/github/release/atline/dapeng.svg)](#)
[![GitHub Release Date](https://img.shields.io/github/release-date/atline/dapeng.svg)](#)
[![GitHub license](https://img.shields.io/github/license/atline/dapeng.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/atline/dapeng.svg)](#)

Dapeng is a distributed scheduler service aims to dispatch tasks to proper resources.

## Online Documentation

You can find the latest Dapeng documentation, including a programming
guide, on the [project web page](#).
This README file only contains basic setup instructions.

## Build Dapeng

Dapeng is built using [Sbt](https://www.scala-sbt.org/).
To build Dapeng, run:

    sbt distclean release

You can also use docker image to build dapeng:

    docker --rm -v /path/to/build.sbt:/dpsrc atline/dpbuild

## Install Dapeng

### System Requirements

##### Hardware Prerequisites

> Processor >= 4 cores

> Memory >= 8 GB RAM

> Hard disk >= 120 GB

##### Software Prerequisite

> OS: Linux distribution, 64 bits

> Kernel Version >= 3.10

> Docker: Enabled

> SSH: Enabled

**NOTE**: You can use `curl -fsSL get.docker.com -o get-docker.sh | sh` to install docker or visit [docker offical website](https://docs.docker.com/install/linux/docker-ce/ubuntu/) to get the latest guide.

### Boot Script

User can use boot script to install/configure/start dapeng base on the component choise.

(You can use curl -O to get the `dp.init`. If not accessable, you can also get it from sourcecode of dapeng, located at `docker/script/dp.init`)

Boot script command usage:

    $ dp.init -c $component --install #  install specified component to your server
    $ dp.init -c $component --configure #  reconfigure specified component
    $ dp.init -c $component --start #  start specified component if not auto start it in install
    NOTE: --start just be used after you install or configure component without choose component auto start.
          In fact, no need to use it in normal case as component will automatically start when server restart.

NOTE: `dp.init` internal use `dpinit` to install/configure/start dapeng components:

[![Docker Build Status](https://img.shields.io/docker/build/atline/dpinit.svg?label=docker(dpinit))](https://hub.docker.com/r/atline/dpinit/builds/)
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpinitbase.svg?label=docker(dpinitbase))](https://hub.docker.com/r/atline/dpinitbase/builds/)

It supports 3 kinds of component, user can install all components on one server. Of course, also can install them on different servers for different aims.


#### dps

[![Docker Build Status](https://img.shields.io/docker/build/atline/dps.svg?label=docker(dps))](https://hub.docker.com/r/atline/dps/builds/)

This component is for dapeng scheduler server, it affords resource router service for user request.

    $ dp.init -c dps --install #  install dps to your server
    $ dp.init -c dps --configure #  reconfigure dps
    $ dp.init -c dps --start #  start dps if not auto start it in install

#### dpm
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpm.svg?label=docker(dpm))](https://hub.docker.com/r/atline/dpm/builds/)
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpmbase.svg?label=docker(dpmbase))](https://hub.docker.com/r/atline/dpmbase/builds/)

This component is for dapeng mesos framework, it affords the adapter between mesos & dapeng scheduler.

    $ dp.init -c dpm --install #  install dpm to your server
    $ dp.init -c dpm --configure #  reconfigure dpm
    $ dp.init -c dpm --start #  start dpm if not auto start it in install

#### dpc
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpc.svg?label=docker(dpc))](https://hub.docker.com/r/atline/dpc/builds/)

This component is for dapeng client, it affords interface for user application to use dapeng scheduler service, meanwhile, also afford path for scheduler to call user application.

    $ dp.init -c dpc --install #  install dpc to your server
    $ dp.init -c dpc --configure #  reconfigure dpc
    $ dp.init -c dpc --start #  start dpc if not auto start it in install

## Dapeng User Script

After install dapeng component, user can use `dp` command to interact with dapeng.

    $ dp xxx
