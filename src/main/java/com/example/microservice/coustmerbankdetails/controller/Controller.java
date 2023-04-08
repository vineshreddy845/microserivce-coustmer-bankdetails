package com.example.microservice.coustmerbankdetails.controller;

import com.example.microservice.coustmerbankdetails.back.Back;
import com.example.microservice.coustmerbankdetails.coustmerdeatils.Coustmerdetailresponses;
import com.example.microservice.coustmerbankdetails.model.Model;
import com.example.microservice.coustmerbankdetails.repository.Repository;
import com.example.microservice.coustmerbankdetails.responses.Responses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/bankdetails")
public class Controller {

    @Autowired
    private Repository repo;
    @Autowired
    private ServiceInstance serviceInstance;
    @Autowired
    private Responses responses;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Coustmerdetailresponses coustmerdetailresponses;
    @Autowired
    private Back back;

    //http://localhost:8081/bankdetails/save
    @PostMapping("/save")
    public Model save(@RequestBody Model model){

       return repo.save(model);
    }
     @PutMapping("/findbyid/{id}")
    public Model update(@RequestBody Model mode, @PathVariable(value="id") Long id){
       //Model model1 = repo.findBybranchname(branchname).get();
       Model model1 = repo.findById(id).get();
        model1.setCoustmerid(mode.getCoustmerid());
        return repo.save(model1);
    }
   // calling from another microserivce. name is microserivcecoustmerdetails
    @GetMapping("/checking/{coustmerid}")
    public Model response(@PathVariable(value="coustmerid") Long coustmerid){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Calling from coustmer-details service coustmerid: {}", coustmerid);
        logger.debug("Calling from coustomer-details service coustmerid: {}", coustmerid);
        URI uri =serviceInstance.getUri();
        System.out.println(uri);

        return repo.findBycoustmerid(coustmerid);
    }

    // we are calling another restapi.
    // below we hardcoded in restapi with coustmerbankdetails change that.
    // below we are getting response from coustmerdetails. with string name and coustmerdetails and bankdetails.
    // so that response should injected to that varibles with same varible name another restapi have.
    @GetMapping("/circuitbreaker/{coustmerid}/{name}")

    public Back rep(@PathVariable(value="coustmerid") Long coustmerid,@PathVariable(value="name")String name){

     //back.setResponses(repo.findBycoustmerid(coustmerid));
   ServiceInstance serviceInstance1= loadBalancerClient.choose(name);

    URI uri=serviceInstance1.getUri();
   // responses.setCoustmerdetailresponses(restTemplate.getForObject(uri+"/details/checkss/{coustmerid}/{name}", Coustmerdetailresponses.class,coustmerid,name));
       // responses.setServiceInstance(restTemplate.getForObject(uri+"/details/uri",ServiceInstance.class));
      back.setResponses(restTemplate.getForObject(uri+"/details/checkss/{coustmerid}/{name}",Responses.class,coustmerid,"COUSTMERBANKDETAILS"));
     return back;

    }
    // adding Data structures concepts.
    //List is collection of elements .It allow duplicate values.
    // List is interface implemented by Linkedlist and ArrayList.
    // Linkedlist means it has node which contains value and address of next node.it store data in different address.
    //ArrayList it stores data squentally. it add memory dynamically.
    // array can't be add dynamically.
    //In arraylist you can access data quickly by placing index number. Linkedlist you can't because it will store data diferent memory address.
    // There are so many methods in Linkedlist and ArrayList.
    // Linkedlist also add memory or array dynamically.
    //types of linkedlist. singlelinkedlist and double linkedlist and circluar list.
    @GetMapping("/linkedlist")
    public Long Data(){
      //  List<Long> list = new LinkedList();
        List<Long>list = new ArrayList<>();
      list.addAll(repo.findAllCustomerIds()) ;
      list.remove(0);
      list.add(0,7L);

  return list.get(3);
    }
    @GetMapping("/linkedlist1")
    public Long Data1(){
         List<Long> list = new LinkedList();
        list.addAll(repo.findAllCustomerIds()) ;
        return list.get(3);
    }
    @GetMapping("/linkedlists/{coustmerid}")
    public List<Model> Data1(@PathVariable(value="coustmerid") Long coustmerid){
        List<Model> list = new LinkedList();
        list.add(repo.findBycoustmerid(coustmerid)) ;


        return list;
    }

    // Stack class
    // Stack is collection of elements. follows Last-in-first-out concept.it uses push opeartion push data in to stack and  it uses pop operation to delete data from stack.
    //stack is implemented using arry(constant size) , Arraylist(dynamicarry it add size depending on requried ),Linkedlist.
    @GetMapping("/linkedlist2")
    public Long Data2(){
      //  Stack<Long> stack = new Stack<Long>()<;
       // Stack<Long> stack1 = new LinkedList<>();
        Deque<Long> deque = (Deque<Long>) new ArrayList<Long>(); // i will get you back.
      return null;
    }

    // set is collection of elements. it doesn't allow duplicate values.
    // set is implemented by Hashset,LinkedHashSet,Tree Set
    public Long data1(){
       // Set<Long> set = new ArrayList<>();
        return null;
    }
    //Queue is collection of elements. it follows first-in-first-out concept.
    //insert at rear and deleted at front.
    //Queue is implemented by Linkedlist, priorityQueue.
    // In linkedlist we add element at middle and front or anywhere.but when you implement queue with linkedlist.it is not possible.
    //*** we are implementing queue with linkedlist. so we can use both queue methods and linkedlist methods. but linked list meyhods addfirst().may voilate (fifo).
    //** it recommend to use queue methods.add(),remove(),offer(),poll().
    //** diferent queues are.like queue,deque,priority queue,circular queue.
    @GetMapping("/Queue")
    public Queue<Long> Data4(){
        Queue<Long> queue = new LinkedList<>();
        queue.addAll(repo.findAllCustomerIds());
        queue.add(7L);
        return queue;
    }
    //A priority queue is a data structure that stores elements in a queue-like manner, but each element has a priority associated with it.
    // Elements with higher priority are dequeued(deleted) before elements with lower priority.
    //If two elements have the same priority, they are dequeued in the order they were enqueued.
    // ** when we implement queue with priority queue.we get question what is the size of queue or priority queue?
    //** answer is priority queue is implemented with binary heap datastructure.
    @GetMapping("/priorityqueue")
    public Queue<Integer> data5(){
//        Comparator<Long> descendingComparator = new Comparator<Long>() { // we are defining prirority order.
//            @Override
//            public int compare(Long o1, Long o2) {
//                return o2.compareTo(o1);
//            }
//        };
//
//     Queue<Long> queue = new PriorityQueue<>(descendingComparator);
      //  queue.addAll(repo.findAllCustomerIds());
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(4);
        queue.add(3);
        queue.add(1);
        queue.add(5); // 1,4,3,5
        queue.remove(); // removed 1
        queue.remove();// removed 3.
        System.out.println(queue);
        // queue.remove();// it is removing 1 from queue. because it defined highest priority low to high.
         // 1 is highest priority. 2 is second.
         return queue; // output 4,5 .
//   but queue follows first-in-first-out. concept right. why it will delete 1.
//   if it delete 1. what is the use of queue implemented with priority queue. queue is not following fifo right?
//
//   Answer:You are correct that the fundamental concept of a queue is to follow the first-in-first-out (FIFO) rule.
//     However, when a queue is implemented using a priority queue, the elements are not necessarily removed in the order they were added.
//In a priority queue, elements are assigned a priority and the element with the highest priority is removed first. In your example,
// the element with the lowest number (1) has the highest priority, so it would be removed first by the remove() method.
//So, even though the queue is implemented using a priority queue, it still maintains the idea of removing elements in a particular order, but that order is determined by the assigned priority rather than the order of insertion.

    }

    //deque:A collection of elements in which elements are added and deleted both sides front and rear.
    //it is impelented with linkedlist.
    // important below
    //That's correct, a deque doesn't strictly follow the FIFO (First-In-First-Out) rule like a normal queue.
    // In a deque, you can add and remove elements from both ends, whereas in a regular queue,
    // elements can only be added at the back and removed from the front, thus strictly following the FIFO rule.
    @GetMapping("/deque")
    public Object Data5() {
        Deque deque = new LinkedList();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);
        deque.addLast(5);
        return deque.removeFirst(); // queue will be like these 3,2,1,4,5. remove.first().removes 3.
        // remove.last().removes 5
      //  Sure, here's an example deque with 5 elements inserted:
        // deque: [ ] (empty deque)
        //Add first: A
       // deque: [A]
//        Add first: B
//                arduino
//        Copy code
//        deque: [B, A]
//        Add last: C
//                arduino
//        Copy code
//        deque: [B, A, C]
//        Remove first:
//        arduino
//        Copy code
//        deque: [A, C]
//        Add first: D
//                mathematica
//        Copy code
//        deque: [D, A, C]
//        Remove last:
//        arduino
//        Copy code
//        deque: [D, A]
//        Final deque: [D, A]
    }

}
