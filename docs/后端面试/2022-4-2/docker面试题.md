1. 什么Docker？
    Docker是一个容器化平台，它以容器的形式将您的应用程序及其所有依赖项打包在一起，以确保您的应用程序在开发，测试或生产的任何环境中无缝运行。
    Docker容器，将一个软件包装在一个完整的文件系统中，该文件系统包含运行所需的一切：代码，运行时，系统工具，系统库等可以安装在服务器上的任何东西。
    这可以保证软件始终运行相同，无论其环境如何。

2. Docker 和虚拟机有何 不同
    Docker不是虚拟化方法。
        它依赖于实际实现基于容器的虚拟化或操作系统级虚拟化的其他工具。为此，Docker最初使用LXC驱动程序，然后移动到libcontainer现在重命名为runc。Docker主要专注于在应用程序容器内自动部署应用程序。应用程序容器旨在打包和运行单个服务，而系统容器则设计为运行多个进程，如虚拟机。因此，Docker被视为容器化系统上的容器管理或应用程序部署工具。Docker是轻量级的沙盒，在其中运行的只是应用，虚拟机里面还有额外的系统。

    容器不需要引导操作系统内核，因此可以在不到一秒的时间内创建容器。此功能使基于容器的虚拟化比其他虚拟化方法更加独特和可取。由于基于容器的虚拟化为主机增加了很少或没有开销，因此基于容器的虚拟化具有接近本机的性能

    对于基于容器的虚拟化，与其他虚拟化不同，不需要其他软件。主机上的所有容器共享主机的调度程序，从而节省了额外资源的需求。与虚拟机映像相比，容器状态（Docker或LXC映像）的大小很小，因此容器映像很容易分发。

    容器中的资源管理是通过cgroup实现的。Cgroups不允许容器消耗比分配给它们更多的资源。虽然主机的所有资源都在虚拟机中可见，但无法使用。这可以通过在容器和主机上同时运行top或htop来实现。所有环境的输出看起来都很相似。

3. 什么是Docker镜像
    Docker镜像是Docker容器的源代码，Docker镜像用于创建容器。可以使用Dockerfile 与 docker build命令创建一个镜像文件。在使用run启动时它们将生成容器。镜像存储在Docker注册表中，registry.hub.docker.com因为它们可能变得非常大，镜像被设计为由其他镜像层组成，允许在通过网络传输镜像时发送最少量的数据。

4. 什么是Docker容器
    docker容器包括应用程序及其所有依赖的底层项，可以作为操作系统的独立进程运行。Docker容器包括应用程序及其所有依赖项，但与其他容器共享内核，作为主机操作系统上用户空间中的独立进程运行。Docker容器不依赖于任何特定的基础架构：它们可以在任何计算机，任何基础架构和任何云中运行。

5. Docker容器有几种状态
    四种状态：运行、已暂停、重新启动、已退出。可以使用如下命令查看指定状态的容器是否运行正常

6. Dockerfile中最常见的指令是什么
    FROM：指定基础镜像； 
    LABEL：功能是为镜像指定标签
    RUN：运行指定的命令
    CMD：容器启动时要运行的命令
    ADD：拷贝文件，支持拷贝远程主机上的文件
    COPY：拷贝本地文件

7. Dockerfile中的命令COPY和ADD命令有什么区别
    一般而言，虽然ADD并且COPY在功能上类似，但是首选COPY。那是因为它比ADD更易懂。COPY仅支持将本地文件复制到容器中，而ADD具有一些额外的功能(如仅限本地的tar提取和远程URL支持)，这些功能并不是很明显。因此，ADD的最佳用途是将本地tar文件自动提取到镜像中，如ADD rootfs.tar.xz /。COPY与ADD的区别COPY的SRC只能是本地文件，其他用法一致

8. docker常用管理命令
    docker pull 拉取或者更新指定镜像
    docker push 将镜像推送至远程仓库
    docker rm 删除容器
    docker rmi 删除镜像
    docker images 列出所有镜像
    docker ps 列出所有容器

9. 容器与主机之间的数据拷贝命令
    docker cp 命令用于容器与主机之间的数据拷贝
    主机到容器：
    docker cp /www 96f7f14e99ab:/www/

    容器到主机：
    docker cp 96f7f14e99ab:/www /tmp/

10. 启动nginx容器（随机端口映射），并挂载本地文件目录到容器html的命令
    docker run -d -P --name nginx2 -v /home/nginx:/usr/share/nginx/html nginx

11. 解释一下dockerfile的ONBUILD指令
    当镜像用作另一个镜像构建的基础时，ONBUILD指令向镜像添加将在稍后执行的触发指令。如果要构建将用作构建其他镜像的基础的镜像（例如，可以使用特定于用户的配置自定义的应用程序构建环境或守护程序），这将非常有用。

12. 什么是Docker Swarm
    Docker Swarm是Docker的本机群集。它将Docker主机池转变为单个虚拟Docker主机。Docker Swarm提供标准的Docker API，任何已经与Docker守护进程通信的工具都可以使用Swarm透明地扩展到多个主机。

13. 如何在生产中监控Docker
    Docker提供docker stats和docker事件等工具来监控生产中的Docker。我们可以使用这些命令获取重要统计数据的报告。
    Docker统计数据：当我们使用容器ID调用docker stats时，我们获得容器的CPU，内存使用情况等。它类似于Linux中的top命令。
    Docker事件：Docker事件是一个命令，用于查看Docker守护程序中正在进行的活动流。
    一些常见的Docker事件是：attach，commit，die，detach，rename，destroy等。我们还可以使用各种选项来限制或过滤我们感兴趣的事件

14. Docker如何在非Linux系统中运行容器
    通过添加到Linux内核版本2.6.24的名称空间功能，可以实现容器的概念。容器将其ID添加到每个进程，并向每个系统调用添加新的访问控制检查。它由clone（）系统调用访问，该调用允许创建先前全局命名空间的单独实例。
    如果由于Linux内核中可用的功能而可以使用容器，那么显而易见的问题是非Linux系统如何运行容器。Docker for Mac和Windows都使用Linux VM来运行容器。Docker Toolbox用于在Virtual Box VM中运行容器。但是，最新的Docker在Windows中使用Hyper-V，在Mac中使用Hypervisor.framework。

15. 进入容器的方法有哪些？
    使用 docker attach 命令
    使用 exec 命令，例如docker exec -i -t 784fd3b294d7 /bin/bash

16. 解释基本的Docker使用工作流程是怎样的？
    (1)、从Dockerfile开始，Dockerfile是镜像的源代码；
    (2)、创建Dockerfile后，可以构建它以创建容器的镜像。镜像只是“源代码”的“编译版本”，即Dockerfile；
    (3)、获得容器的镜像后，应使用注册表（registry）重新分发容器。注册表就像一个git存储库，可以推送和拉取镜像；接下来，可以使用该镜像运行容器。在许多方面，正在运行的容器与虚拟机(但没有虚拟机管理程序)非常相似。

17. 什么类型的应用程序无状态或有状态更适合Docker容器？
    最好为Docker Container创建无状态应用程序。我们可以从应用程序中创建一个容器，并从应用程序中取出可配置的状态参数。现在我们可以在生产环境和具有不同参数的QA环境中运行相同的容器。这有助于在不同场景中重用相同的镜像。另外，无状态应用程序比有状态应用程序更容易使用Docker容器进行扩展。

18. Docker 安全么？
    Docker 利用了 Linux 内核中很多安全特性来保证不同容器之间的隔离，并且通过签名机制来对镜像进行验证。大量生产环境的部署证明，Docker 虽然隔离性无法与 虚拟机相比，但仍然具有极高的安全性。

19. 如何清理后台停止的容器？
    可以使用 sudo docker rm $sudo( docker ps -a -q) 命令 或者 使用 sudo docker rmi $(sudo docker images -q -f danging=true)命令

20. 如何查看镜像支持的环境变量？
    可以使用 docker run IMAGE env 命令。