      		西 安 邮 电 大 学
            		（计算机学院）

课内实验报告

实验名称：       进程线程实验







   专业名称：        	 计算机科学与技术
  班    级：			 	   计科1503
   学生姓名：  			 	郗宇
  学号（8位）：          04151076
  指导教师：          		     陈莉君
  实验日期：  2017年4月12 日-2017年4月20日



 实验目的及实验环境 
（一） 实验环境 
Linux 操作系统  
（二）实验目的 
实验1  掌握Linux基本命令 和开发环境 
 掌握常用的Linux shell命令； 
2. 掌握编辑环境VIM； 
3. 掌握编译环境gcc及跟踪调试工具gdb。 
    实验2  进程 
通过观察、分析实验现象，深入理解进程及进程在调度执行和内存空间等方面的特点，掌握在POSIX 规范中fork和kill系统调用的功能和使用。
     实验3  线程 
通过观察、分析实验现象，深入理解线程及线程在调度执行和内存空间 等方面的特点，并掌握线程与进程的区别。掌握POSIX 规范中 pthread_create() 函数的功能和使用方法。  
     实验4  互斥 
通过观察、分析实验现象，深入理解理解互斥锁的原理及特点掌握在POSIX 规范中的互斥函数的功能及使用方法。
实验内容
实验2
你最初认为运行结果会怎么样？
大于10就输出10个进程的内容和id，少于10输出给定个进程和id 
实际的结果什么样？有什么特点？试对产生该现象的原因进行分析。
大于10就以10来创建进程。随机输出0~9号进程，一直循环输出，输入一个数字，会杀死该数字id的进程，直到输入q退出循环，然后杀死所有的进程。
分析：定义了10个pid_t，主进程先判断定义的子线程的个数，我们定义的10,然后创建10个子进程，调用dosomething函数死循环，每两秒输出进程id。输入进程id,会杀死这个id的进程。输入q循环退出，kill(0,SIGTERM)，杀死本组所有进程，程序退出。
proc_number这个全局变量在各个子进程里的值相同吗？为什么？
proc_number在各个子进程里的值相同，因为这是子进程相互独立的资源，所以互不影响。
kill 命令在程序中使用了几次？每次的作用是什么？执行后的现象是什么？
使用了2次。
第一次按进程号杀死进程kill(pid[ch-'0'],SIGTERM)，执行后接下来的结果中不会有该进程号。
第二次是杀死本组所有进程， for(i=0;i<child_proc_number;i++){
     kill(pid[i],SIGTERM);。杀死主进程以和它创建的所有子进程。执行后程序退         	出，进程结束。
使用kill 命令可以在进程的外部杀死进程。进程怎样能主动退出？这两种退出方式哪种更好一些？
进程在main函数中return，或调用exit()函数都可以正常退出。 而使
用kill命令则是异常退出，产生僵尸进程。
实验3
1. 你最初认为前三列数会相等吗？最后一列斜杠两边的数字是相等，还是大于或者小于关系？ 
前三列数不会相等，因为三个线程运行次数是随机的，counter[i]一定不会相等。main_counter与sum值应该是相等的,因为都是三个线程的counter之和。
最后的结果如你所料吗？有什么特点？对原因进行分析。 
实验结果是前三列数确实不相等。不过main_counter与sum的值也不相等，main_counter < sum,因为三个线程在共同争取运行thread_worker()函数，比如main_counter初值为0，pthread_id[0]执行之后main_counter+1，此时还未来得及将值赋给main_counter，这时的main_counter还是0；pthread_id[1]也执行这个函数，main_counter+1，若此时在1号线程将main_counter+1的值还未赋给main_counter，即这时的main_counter还是0，pthread_id[2]也来执行这个函数，main_counter+1，此时三个线程才将加完之后的值赋给main_counter，则main_counter=0+1=1，而真正执行次sum=0+1+1+1=3。main_counter < sum。
thread 的CPU 占用率是多少？为什么会这样？ 
thread的CPU占用率在我的虚拟机中执行结果是101，因为三个线程是无限循环的运行，使得cpu占用率很高。
4. thread_worker()内是死循环，它是怎么退出的？你认为这样退出好吗？
thread_worker()函数内是死循环，因为主函数中设置的输入q时循环退出。输入q时主进程执行退出，return 退出程序，则子线程也强制退出。这样退出不好。
实验4
你预想deadlock.c 的运行结果会如何？
程序运行中出现终止现象，可能会资源互斥。
deadlock.c 的实际运行结果如何？多次运行每次的现象都一样吗？为什么会这样？
实际运行时程序会在运行期间中止，出现死锁现象。多次运行之后
现象都一样。原因是：主线程申请mutex1资源，而子线程申请mutex2资源，此时主线程继续申请mutex2资源，子线程来申请mutex1资源，而mutex2资源还未被子线程释放，主线程无法申请到，同样的，mutex1资源未被主线程释放则子线程也无法申请到，此时便处于无限循环等待，形成死锁。
实验源代码
进程
#include<ctype.h>

#define MAX_CHILD_NUMBER 10
#define SLEEP_INTERVAL 2

int proc_number=0;
void do_something();

int main(int argc,char*argv[]){
        int child_proc_number=MAX_CHILD_NUMBER;
        int i,ch;
        pid_t child_pid;
        pid_t pid[10]={0};
        if(argc>1){
                child_proc_number=atoi(argv[1]);
                child_proc_number=(child_proc_number>10)?10:child_proc_number;
        }
        for(int i=0;i<child_proc_number;i++){
                child_pid =fork();
                if(child_pid==0){
                        proc_number=i;
                        do_something();

                }else{
                        pid[i]==child_pid;
                }

        }
        while((ch=getchar())!='q'){
                if(isdigit(ch)){
                        kill(pid[ch-'0'],SIGTERM);
                        printf("kill_child_process:\n");
                }
        }
        sleep(SLEEP_INTERVAL);
        for(i=0;i<child_proc_number;i++){
                kill(pid[i],SIGTERM);
        }
        printf("kill_all_process");//杀死本组所有进程
        return ;
}
        void do_something(){
                for(;;){
                        printf("This is process NO.%d and it's pid is %d\n",proc_number,getpid());
                sleep(SLEEP_INTERVAL);//阻塞两秒

                }
        }

                                                                             
线程
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <ctype.h>
#include <pthread.h>
#include<semaphore.h>

#define MAX_THREAD 3 /* 线程的个数 */
unsigned long long main_counter, counter[MAX_THREAD];
pthread_mutex_t mutex2 = PTHREAD_MUTEX_INITIALIZER;
/* unsigned long  long是比long还长的整数 */

        sem_t s1;

        void* thread_worker(void*);
//        int pthread_create(pthread_t*restrict tidp,const pthread_attr_t*restrict 	attr,void*(*start_rtn)(void),void*restrict arg);
         int main(int argc, char* argv[])
        {
                 int i, rtn, ch,rc;
                 sem_init(&s1,0,1);

                 pthread_t pthread_id[MAX_THREAD] = {0}; /* 存放线程id*/
                 for (i=0; i<MAX_THREAD; i++)    {
                 /* 在这里填写代码，用pthread_create建一个普通的线程，
                   线程id存入pthread_id[i]，线程执行函数是thread_worker
                 并i作为参数传递给线程 */
                rc=pthread_create(&pthread_id[i],NULL,thread_worker,(void*)i);
                if(rc!=0)
                        printf("THread create failed;\n");
                 }
                 do{
                        /* 用户按一次回车执行下面的循环体一次。按q退出 */
                         unsigned long long sum = 0;
                         /* 求所有线程的counter的和 */

                         for (i=0; i<MAX_THREAD; i++)  {

                         /* 求所有counter的和 */
                pthread_mutex_lock(&mutex2);
                 sum += counter[i];
                 printf("%llu ", counter[i]);
       pthread_mutex_unlock(&mutex2);

                         }
                         printf("%llu/%llu", main_counter, sum);
                 }  while ((ch = getchar()) != 'q');
                 return 0;
        }
        void* thread_worker(void* i) {
               unsigned long long  thread_num;
                /* 在这里填写代码，把main中的i的值传递给thread_num */
                thread_num=i;
                for(;;) {
                 /* 无限循环 */
                 pthread_mutex_lock(&mutex2);
                     counter[thread_num]++; /* 本线程的counter加一 */
                    main_counter++; /* 主counter 加一 */
                  pthread_mutex_unlock(&mutex2);
                }
        }
互斥
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <ctype.h>
#include <pthread.h>

#define LOOP_TIMES 10000 
pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;
           /*用宏PTHREAD_MUTEX_INITIALIZER来初始化 */
pthread_mutex_t mutex2 = PTHREAD_MUTEX_INITIALIZER;
void* thread_worker(void*);
void critical_section(int thread_num, int i);
int main(void)  {
      int rtn, i;
      pthread_t pthread_id = 0; /* 存放子线程的id */
      rtn = pthread_create(&pthread_id, NULL, thread_worker, NULL );
      if(rtn != 0) {
            printf("pthread_create ERROR!\n");
            return -1;
      }
      for (i=0; i<LOOP_TIMES; i++)  {
        pthread_mutex_lock(&mutex1);
        pthread_mutex_lock(&mutex2);
        critical_section(1, i);
        pthread_mutex_unlock(&mutex2);
        pthread_mutex_unlock(&mutex1);
      }
pthread_mutex_destroy(&mutex1);
     pthread_mutex_destroy(&mutex2);

     sleep(5);
     return 0;
}
void* thread_worker(void* p)  {
      int i;
      for (i=0; i<LOOP_TIMES; i++) {
        pthread_mutex_lock(&mutex1);
        pthread_mutex_lock(&mutex2);
        critical_section(2, i);
        pthread_mutex_unlock(&mutex2);
        pthread_mutex_unlock(&mutex1);


      }
    }

void critical_section(int thread_num, int i) {
      printf("Thread%d: %d\n", thread_num,  i);
}
四、实验总结
在本次操作系统实验中，了解了进程线程相关知识，fork，kill。对程序的运行状态和单位都有了一定的了解。