# Dapeng

[![Travis](https://img.shields.io/travis/atline/dapeng.svg)](https://travis-ci.org/atline/dapeng/)
[![GitHub release](https://img.shields.io/github/release/atline/dapeng.svg)](#)
[![GitHub Release Date](https://img.shields.io/github/release-date/atline/dapeng.svg)](#)
[![GitHub license](https://img.shields.io/github/license/atline/dapeng.svg)](#)
[![GitHub top language](https://img.shields.io/github/languages/top/atline/dapeng.svg)](#)

Dapeng is a distributed scheduler service aims to dispatch tasks to proper resources.


## Online Documentation

You can find the latest Dapeng documentation on the [project web page](#)[TODO].<br />
This README file only contains basic setup instructions.


## Build Dapeng

Dapeng is built using [Sbt](https://www.scala-sbt.org/).
To build Dapeng, run:

    sbt distclean release

You can also use `dpbuild docker image` to build dapeng:

[![Docker Build Status](https://img.shields.io/docker/build/atline/dpbuild.svg?label=docker(dpbuild))](https://hub.docker.com/r/atline/dpbuild/builds/)

    docker pull atline/dpbuild
    docker run --rm -v /path/to/dapeng_repo:/dpsrc atline/dpbuild


## Install Dapeng

### 1. System Requirements

> **Hardware Prerequisites**
> 
>     Processor >= 4 cores
>     Memory >= 8 GB RAM
>     Hard disk >= 120 GB

> **Software Prerequisite**
> 
>     OS: Linux distribution, 64 bits
>     Kernel Version >= 3.10
>     Docker: Enabled
>     SSH: Enabled

**NOTE**: You can use `curl https://get.docker.com/ | sudo sh` to install docker or visit [docker official website](https://docs.docker.com/install/linux/docker-ce/ubuntu/) to get the latest guide.

### 2. Install Dapeng Components

User can use boot script to install/configure/start dapeng components.

(You can use `curl -O https://bitbucket.sw.nxp.com/projects/MSVC/repos/dapeng/raw/docker/script/dp.init` to get the `dp.init`. If not accessable, you can also get it from sourcecode of dapeng, located at `docker/script/dp.init`)

When use `dp.init` to install dapeng component, an environment variable must be set to determine the version to be installed, this environment variable just needed when install.

    $ export DP_VERSION=$version

Boot script command usage:

    $ dp.init -c $component --install #  install specified component to your server
    $ dp.init -c $component --configure #  reconfigure specified component
    $ dp.init -c $component --start #  start specified component if not auto start it in install
      NOTE: --start just be used after you install or configure component without choose component auto start.
            In fact, no need to use it in normal case as component will automatically start when server restart.

NOTE: `dp.init` internal use `dpinit docker image` to install/configure/start dapeng components:

[![Docker Build Status](https://img.shields.io/docker/build/atline/dpinit.svg?label=docker(dpinit))](https://hub.docker.com/r/atline/dpinit/builds/)
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpinitbase.svg?label=docker(dpinitbase))](https://hub.docker.com/r/atline/dpinitbase/builds/)

It supports 3 kinds of components, belongs to server & client parts:

#### 2.1. Server Parts

#### infrastructure software

    # install zookeeper
    docker run -d --net=host --name=dp-zookeeper zookeeper:3.4.10

    # install redis
    docker run -d --net=host --name=dp-redis redis:3.2

    # install mesos-master
    docker run -d --net=host --name=mesos-master \
      -e MESOS_IP=$MESOS_IP \
      -e MESOS_PORT=5050 \
      -e MESOS_ZK=zk://$ZOOKEEPER_IP:2181/mesos \
      -e MESOS_QUORUM=1 \
      -e MESOS_REGISTRY=in_memory \
      -e MESOS_LOG_DIR=/var/log/mesos \
      -e MESOS_WORK_DIR=/var/tmp/mesos \
      -v "$(echo ~)/.dp/mesos-master/log/mesos:/var/log/mesos" \
      -v "$(echo ~)/.dp/mesos-master/tmp/mesos:/var/tmp/mesos" \
      mesosphere/mesos-master:1.3.0

#### component: dps

[![Docker Build Status](https://img.shields.io/docker/build/atline/dps.svg?label=docker(dps))](https://hub.docker.com/r/atline/dps/builds/)

This component is for dapeng scheduler server, it affords resource router service for user request.

    $ dp.init -c dps --install #  install dps to your server
    $ dp.init -c dps --configure #  reconfigure dps
    $ dp.init -c dps --start #  start dps if not auto start it in install

#### component: dpm
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpm.svg?label=docker(dpm))](https://hub.docker.com/r/atline/dpm/builds/)
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpmbase.svg?label=docker(dpmbase))](https://hub.docker.com/r/atline/dpmbase/builds/)

This component is for dapeng mesos framework, it affords the adapter between mesos & dapeng scheduler.

    $ dp.init -c dpm --install #  install dpm to your server
    $ dp.init -c dpm --configure #  reconfigure dpm
    $ dp.init -c dpm --start #  start dpm if not auto start it in install

#### 2.2. Client Parts

#### component: dpc
[![Docker Build Status](https://img.shields.io/docker/build/atline/dpc.svg?label=docker(dpc))](https://hub.docker.com/r/atline/dpc/builds/)

This component is for dapeng client, it affords interface for user application to use dapeng scheduler service, meanwhile, also afford path for scheduler to call user application.

    $ dp.init -c dpc --install #  install dpc to your server
    $ dp.init -c dpc --configure #  reconfigure dpc
    $ dp.init -c dpc --start #  start dpc if not auto start it in install

## User Script

After install dapeng component, user can use `dp` command to interact with dapeng.

***Register Node:***

    dp -m join \
      --nodeKey $nodeKey \
      --nodeName $nodeName \
      --nodeData $nodeData \
      --nodeToken $nodeToken \
      --hostIp $hostIp

> ***Parameters:***
> 
>     nodeKey: unique field for one device, e.g. 00:01:02:03:04:05
>     nodeName: display only, can use any prefered label, e.g. IMX6QP-Sabre-SD
>     nodeData: field which participate scheduler match, e.g. i.MX6QP-SABRE_SDB
>     nodeToken: field which participate scheduler match, e.g. IMX6QP-Sabre-SD@1@LATF-Earth
>     hostIp: host ip for the device, e.g. 10.192.225.180

***Unregister Node:***

    dp -m leave --nodeKey $nodeKey

> ***Parameters:***
> 
>     nodeKey: unique field for one device, e.g. 00:01:02:03:04:05

***Submit Request:***

    dp -m add --id $id

> ***Parameters:***
> 
>     id: request id, e.g. 1

***Feedback Request Trigger Response:***

    dp -m triggerResponse --id $id --status $status --nodeKey $nodeKey

> ***Parameters:***
> 
>     id: request id, e.g. 1
>     status: trigger status, e.g. 12
>     nodeKey: unique field for one device, e.g. 00:01:02:03:04:05

***Submit Result:***

    dp -m result --id $id --taskId $taskId --result $result --attachmentLoc $attachmentLoc

> ***Parameters:***
> 
>     id: request id, e.g. 1
>     taskId: task id, e.g. TESTCASE1
>     result: result id, e.g. 1
>     attachmentLoc: task attachment, e.g. 10.192.244.37#/path/to/attachment1#/path/to/attachment2

***Inform Request Finish:***

    dp -m informFinish --id $id

> ***Parameters:***
> 
>     id: request id, e.g. 1

***Stop Request:***

    dp -m stop --id $id

> ***Parameters:***
> 
>     id: request id, e.g. 1
